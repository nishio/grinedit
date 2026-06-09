import type { Edge, Vertex } from "../core/types";
import { GraphModel } from "../core/graph";

interface CanvasPoint {
  x: number;
  y: number;
}

const VERTEX_FILL = "#7ec27d";
const VERTEX_FRAME = "#1e3324";
const SELECTED_FILL = "#f2b84b";
const EDGE_COLOR = "#2c3138";
const SELECTED_EDGE = "#d8513f";

export class CanvasView {
  private graph?: GraphModel;
  private dragging?: Vertex;
  private scale = 1;
  private origin: CanvasPoint = { x: 0, y: 0 };

  constructor(
    private readonly canvas: HTMLCanvasElement,
    private readonly onSelect: (id: string | undefined) => void,
    private readonly onGraphChange: () => void = () => {}
  ) {
    this.canvas.addEventListener("pointerdown", (event) => this.pointerDown(event));
    this.canvas.addEventListener("pointermove", (event) => this.pointerMove(event));
    this.canvas.addEventListener("pointerup", () => this.pointerUp());
    this.canvas.addEventListener("pointerleave", () => this.pointerUp());
  }

  setGraph(graph: GraphModel): void {
    this.graph = graph;
    this.draw();
  }

  draw(): void {
    const ctx = this.canvas.getContext("2d");
    if (!ctx || !this.graph) return;
    this.resize(ctx);
    ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    ctx.save();
    ctx.translate(this.origin.x, this.origin.y);
    ctx.scale(this.scale, this.scale);
    this.drawGrid(ctx);
    for (const edge of this.graph.edges.values()) {
      this.drawEdge(ctx, edge);
    }
    for (const vertex of this.graph.vertices.values()) {
      this.drawVertex(ctx, vertex);
    }
    ctx.restore();
  }

  private resize(ctx: CanvasRenderingContext2D): void {
    const ratio = window.devicePixelRatio || 1;
    const rect = this.canvas.getBoundingClientRect();
    const width = Math.max(1, Math.floor(rect.width * ratio));
    const height = Math.max(1, Math.floor(rect.height * ratio));
    if (this.canvas.width !== width || this.canvas.height !== height) {
      this.canvas.width = width;
      this.canvas.height = height;
    }
    this.scale = ratio;
    this.origin = { x: width / 2, y: height / 2 };
    ctx.setTransform(1, 0, 0, 1, 0, 0);
  }

  private drawGrid(ctx: CanvasRenderingContext2D): void {
    const size = 40;
    const width = this.canvas.width / this.scale;
    const height = this.canvas.height / this.scale;
    ctx.strokeStyle = "#e3e6e8";
    ctx.lineWidth = 1 / this.scale;
    for (let x = -width; x <= width; x += size) {
      ctx.beginPath();
      ctx.moveTo(x, -height);
      ctx.lineTo(x, height);
      ctx.stroke();
    }
    for (let y = -height; y <= height; y += size) {
      ctx.beginPath();
      ctx.moveTo(-width, y);
      ctx.lineTo(width, y);
      ctx.stroke();
    }
  }

  private drawEdge(ctx: CanvasRenderingContext2D, edge: Edge): void {
    if (!this.graph) return;
    const v1 = this.graph.vertices.get(edge.v1);
    const v2 = this.graph.vertices.get(edge.v2);
    if (!v1 || !v2) return;
    ctx.strokeStyle = edge.selected ? SELECTED_EDGE : rgb(edge.color, EDGE_COLOR);
    ctx.lineWidth = edge.classname === "BasicStrokeEdge" ? Math.max(1, edge.width) : 1.5;
    ctx.beginPath();
    ctx.moveTo(v1.x, v1.y);
    ctx.lineTo(v2.x, v2.y);
    ctx.stroke();
    if (edge.classname === "TriangleEdge") {
      this.drawArrow(ctx, v1, v2);
    }
  }

  private drawArrow(ctx: CanvasRenderingContext2D, from: Vertex, to: Vertex): void {
    const angle = Math.atan2(to.y - from.y, to.x - from.x);
    const length = 12;
    ctx.save();
    ctx.translate(to.x, to.y);
    ctx.rotate(angle);
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(-length, -length / 2);
    ctx.lineTo(-length, length / 2);
    ctx.closePath();
    ctx.fillStyle = ctx.strokeStyle;
    ctx.fill();
    ctx.restore();
  }

  private drawVertex(ctx: CanvasRenderingContext2D, vertex: Vertex): void {
    const label = vertex.label || vertex.id;
    ctx.font = "13px system-ui, sans-serif";
    const width = Math.max(34, ctx.measureText(label).width + 18);
    const height = 28;
    ctx.fillStyle = vertex.selected ? SELECTED_FILL : VERTEX_FILL;
    ctx.strokeStyle = VERTEX_FRAME;
    ctx.lineWidth = 1.5;
    if (vertex.classname === "CircleVertex") {
      const radius = Math.max(width, height) / 2;
      ctx.beginPath();
      ctx.arc(vertex.x, vertex.y, radius, 0, Math.PI * 2);
      ctx.fill();
      ctx.stroke();
    } else {
      roundRect(ctx, vertex.x - width / 2, vertex.y - height / 2, width, height, 4);
      ctx.fill();
      ctx.stroke();
    }
    ctx.fillStyle = "#111820";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText(label, vertex.x, vertex.y);
  }

  private pointerDown(event: PointerEvent): void {
    if (!this.graph) return;
    const point = this.eventPoint(event);
    const vertex = this.nearestVertex(point);
    this.graph.clearSelection();
    if (vertex) {
      vertex.selected = true;
      this.dragging = vertex;
      this.canvas.setPointerCapture(event.pointerId);
      this.onSelect(vertex.id);
    } else {
      this.onSelect(undefined);
    }
    this.draw();
  }

  private pointerMove(event: PointerEvent): void {
    if (!this.dragging) return;
    const point = this.eventPoint(event);
    this.dragging.x = point.x;
    this.dragging.y = point.y;
    this.dragging.params.x = point.x;
    this.dragging.params.y = point.y;
    this.onGraphChange();
  }

  private pointerUp(): void {
    this.dragging = undefined;
  }

  private eventPoint(event: PointerEvent): CanvasPoint {
    const rect = this.canvas.getBoundingClientRect();
    const ratio = window.devicePixelRatio || 1;
    return {
      x: (event.clientX - rect.left) * ratio / this.scale - this.origin.x / this.scale,
      y: (event.clientY - rect.top) * ratio / this.scale - this.origin.y / this.scale
    };
  }

  private nearestVertex(point: CanvasPoint): Vertex | undefined {
    if (!this.graph) return undefined;
    let nearest: Vertex | undefined;
    let nearestDistance = 26;
    for (const vertex of this.graph.vertices.values()) {
      const distance = Math.hypot(point.x - vertex.x, point.y - vertex.y);
      if (distance < nearestDistance) {
        nearest = vertex;
        nearestDistance = distance;
      }
    }
    return nearest;
  }
}

function rgb(color: [number, number, number], fallback: string): string {
  if (!color.some((value) => value !== 0)) return fallback;
  return `rgb(${color.map((value) => Math.max(0, Math.min(255, value))).join(" ")})`;
}

function roundRect(ctx: CanvasRenderingContext2D, x: number, y: number, w: number, h: number, r: number): void {
  ctx.beginPath();
  ctx.moveTo(x + r, y);
  ctx.lineTo(x + w - r, y);
  ctx.quadraticCurveTo(x + w, y, x + w, y + r);
  ctx.lineTo(x + w, y + h - r);
  ctx.quadraticCurveTo(x + w, y + h, x + w - r, y + h);
  ctx.lineTo(x + r, y + h);
  ctx.quadraticCurveTo(x, y + h, x, y + h - r);
  ctx.lineTo(x, y + r);
  ctx.quadraticCurveTo(x, y, x + r, y);
  ctx.closePath();
}
