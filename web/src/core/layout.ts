import type { Edge, Vertex } from "./types";
import { GraphModel } from "./graph";

export interface LayoutOptions {
  springLength: number;
  springStrength: number;
  repulsionStrength: number;
  damping: number;
  timeStep: number;
}

const DEFAULT_LAYOUT: LayoutOptions = {
  springLength: 120,
  springStrength: 0.012,
  repulsionStrength: 2800,
  damping: 0.82,
  timeStep: 1
};

function edgeVertices(graph: GraphModel, edge: Edge): [Vertex, Vertex] | undefined {
  const v1 = graph.vertices.get(edge.v1);
  const v2 = graph.vertices.get(edge.v2);
  return v1 && v2 ? [v1, v2] : undefined;
}

export function layoutStep(graph: GraphModel, options: Partial<LayoutOptions> = {}): void {
  const opts = { ...DEFAULT_LAYOUT, ...options };
  const forces = new Map<string, { x: number; y: number }>();
  for (const vertex of graph.vertices.values()) {
    forces.set(vertex.id, { x: 0, y: 0 });
  }

  for (const edge of graph.edges.values()) {
    const pair = edgeVertices(graph, edge);
    if (!pair) continue;
    const [a, b] = pair;
    const dx = b.x - a.x;
    const dy = b.y - a.y;
    const distance = Math.hypot(dx, dy) || 1;
    const force = (distance - opts.springLength) * opts.springStrength;
    const fx = (dx / distance) * force;
    const fy = (dy / distance) * force;
    forces.get(a.id)!.x += fx;
    forces.get(a.id)!.y += fy;
    forces.get(b.id)!.x -= fx;
    forces.get(b.id)!.y -= fy;
  }

  const vertices = [...graph.vertices.values()];
  for (let i = 0; i < vertices.length; i++) {
    for (let j = i + 1; j < vertices.length; j++) {
      const a = vertices[i];
      const b = vertices[j];
      const dx = b.x - a.x;
      const dy = b.y - a.y;
      const distanceSq = Math.max(dx * dx + dy * dy, 25);
      const distance = Math.sqrt(distanceSq);
      const force = opts.repulsionStrength / distanceSq;
      const fx = (dx / distance) * force;
      const fy = (dy / distance) * force;
      forces.get(a.id)!.x -= fx;
      forces.get(a.id)!.y -= fy;
      forces.get(b.id)!.x += fx;
      forces.get(b.id)!.y += fy;
    }
  }

  for (const vertex of graph.vertices.values()) {
    if (vertex.pinned) {
      vertex.vx = 0;
      vertex.vy = 0;
      graph.syncVertexParams(vertex);
      continue;
    }
    const force = forces.get(vertex.id)!;
    vertex.vx = (vertex.vx + force.x * opts.timeStep) * opts.damping;
    vertex.vy = (vertex.vy + force.y * opts.timeStep) * opts.damping;
    vertex.x += vertex.vx * opts.timeStep;
    vertex.y += vertex.vy * opts.timeStep;
    graph.syncVertexParams(vertex);
  }
}
