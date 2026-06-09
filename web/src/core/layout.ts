import type { Edge, Vertex } from "./types";
import { GraphModel } from "./graph";

export interface LayoutOptions {
  maxIter: number;
  springLength: number;
  springStrength: number;
  repulsionRadius: number;
  repulsionK: number;
  anchorTolerance: number;
  anchorPixelTolerance: number;
  viewportScale: number;
}

const DEFAULT_LAYOUT: LayoutOptions = {
  maxIter: 10,
  springLength: 1.0,
  springStrength: 0.1,
  repulsionRadius: 3.0,
  repulsionK: 0.02,
  anchorTolerance: 9,
  anchorPixelTolerance: 0.1,
  viewportScale: 40.0
};

function edgeVertices(graph: GraphModel, edge: Edge): [Vertex, Vertex] | undefined {
  const v1 = graph.vertices.get(edge.v1);
  const v2 = graph.vertices.get(edge.v2);
  return v1 && v2 ? [v1, v2] : undefined;
}

export function layoutStep(graph: GraphModel, options: Partial<LayoutOptions> = {}): void {
  const opts = { ...DEFAULT_LAYOUT, ...graphLayoutOptions(graph), ...options };
  const vertices = [...graph.vertices.values()];
  const oldPositions = new Map(vertices.map((vertex) => [vertex.id, { x: vertex.x, y: vertex.y }]));
  const anchorTargets = new Map(
    vertices.filter((vertex) => vertex.pinned).map((vertex) => [vertex.id, { x: vertex.x, y: vertex.y }])
  );

  for (let iter = 0; iter < opts.maxIter; iter++) {
    const dVel = new Map(vertices.map((vertex) => [vertex.id, { x: 0, y: 0 }]));
    let isAllSatisfied = true;

    if (iter === 0) {
      applySpringEdge(graph, dVel, opts);
      applyRepulsion(vertices, dVel, opts);
      if (anchorTargets.size > 0) {
        isAllSatisfied = false;
      }
    }

    if (iter > 0 && iter < opts.anchorTolerance) {
      for (const [id, target] of anchorTargets) {
        const vertex = graph.vertices.get(id);
        if (!vertex) continue;
        const dx = target.x - vertex.x;
        const dy = target.y - vertex.y;
        if (Math.hypot(dx * opts.viewportScale, dy * opts.viewportScale) > opts.anchorPixelTolerance) {
          dVel.get(id)!.x += dx;
          dVel.get(id)!.y += dy;
          isAllSatisfied = false;
        }
      }
    }

    for (const vertex of vertices) {
      const velocity = dVel.get(vertex.id)!;
      vertex.x += velocity.x;
      vertex.y += velocity.y;
    }

    if (isAllSatisfied) {
      break;
    }
  }

  for (const vertex of vertices) {
    const oldPosition = oldPositions.get(vertex.id)!;
    vertex.vx = vertex.x - oldPosition.x;
    vertex.vy = vertex.y - oldPosition.y;
    graph.syncVertexParams(vertex);
  }
}

function graphLayoutOptions(graph: GraphModel): Partial<LayoutOptions> {
  const spring = graph.laws.get("PL_SpringEdge")?.params;
  const repulsion = graph.laws.get("PL_Repulsion")?.params;
  return {
    springLength: numberParam(spring?.defaultNormalLength, DEFAULT_LAYOUT.springLength),
    springStrength: numberParam(spring?.defaultSpringStrength, DEFAULT_LAYOUT.springStrength),
    repulsionK: numberParam(repulsion?.repulsionK, DEFAULT_LAYOUT.repulsionK),
    repulsionRadius: numberParam(repulsion?.repulsionRadius, DEFAULT_LAYOUT.repulsionRadius)
  };
}

function numberParam(value: unknown, fallback: number): number {
  const n = Number(value);
  return Number.isFinite(n) ? n : fallback;
}

function applySpringEdge(
  graph: GraphModel,
  dVel: Map<string, { x: number; y: number }>,
  opts: LayoutOptions
): void {
  for (const edge of graph.edges.values()) {
    const pair = edgeVertices(graph, edge);
    if (!pair) continue;
    const [a, b] = pair;
    const dx = b.x - a.x;
    const dy = b.y - a.y;
    const distance = Math.hypot(dx, dy);
    const nx = distance === 0 ? 1 : dx / distance;
    const ny = distance === 0 ? 0 : dy / distance;
    const power = (opts.springLength - distance) * opts.springStrength;
    const fx = nx * power;
    const fy = ny * power;
    dVel.get(b.id)!.x += fx;
    dVel.get(b.id)!.y += fy;
    dVel.get(a.id)!.x -= fx;
    dVel.get(a.id)!.y -= fy;
  }
}

function applyRepulsion(
  vertices: Vertex[],
  dVel: Map<string, { x: number; y: number }>,
  opts: LayoutOptions
): void {
  const radiusSq = opts.repulsionRadius * opts.repulsionRadius;
  for (let i = 0; i < vertices.length; i++) {
    const a = vertices[i];
    for (let j = 0; j < i; j++) {
      const b = vertices[j];
      const dx = b.x - a.x;
      const dy = b.y - a.y;
      if (dx > opts.repulsionRadius || dx < -opts.repulsionRadius) continue;
      if (dy > opts.repulsionRadius || dy < -opts.repulsionRadius) continue;
      const distanceSq = dx * dx + dy * dy;
      const normalizedDistance = distanceSq / radiusSq;
      if (normalizedDistance >= 1) continue;
      const distance = Math.sqrt(distanceSq);
      const nx = distanceSq === 0 ? 1 : dx / distance;
      const ny = distanceSq === 0 ? 0 : dy / distance;
      const power = opts.repulsionK * (1.0 - normalizedDistance);
      const fx = nx * power;
      const fy = ny * power;
      dVel.get(b.id)!.x += fx;
      dVel.get(b.id)!.y += fy;
      dVel.get(a.id)!.x -= fx;
      dVel.get(a.id)!.y -= fy;
    }
  }
}
