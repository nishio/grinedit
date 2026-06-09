import type { Edge, Vertex } from "../core/types";
import { GraphModel } from "../core/graph";

interface CanvasPoint {
  x: number;
  y: number;
}

const VIEWPORT_SCALE = 40;
const VERTEX_FILL = "rgb(100 200 100)";
const VERTEX_FRAME = "rgb(0 0 0)";
const SELECTED_FILL = "rgb(100 100 200)";
const PINNED_FRAME = "#344e9a";
const EDGE_COLOR = "rgb(0 0 0)";
const SELECTED_EDGE = "rgb(200 100 100)";
const BOX_MARGIN = 3;
const CIRCLE_DIAMETER = 15;

export class CanvasView {
  private graph?: GraphModel;
  private dragging?: Vertex;
  private dragStart?: CanvasPoint;
  private didDrag = false;
  private screenCenter: CanvasPoint = { x: 0, y: 0 };

  constructor(
    private readonly canvas: HTMLCanvasElement,
    private readonly onSelect: (id: string | undefined) => void,
    private readonly onGraphChange: () => void = () => {}
  ) {
    this.canvas.addEventListener("mousedown", (event) => this.mouseDown(event));
    this.canvas.addEventListener("mousemove", (event) => this.mouseMove(event));
    this.canvas.addEventListener("mouseup", (event) => this.mouseUp(event));
    this.canvas.addEventListener("mouseleave", (event) => this.mouseUp(event));
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
    for (const edge of this.graph.edges.values()) {
      this.drawEdge(ctx, edge);
    }
    for (const vertex of this.graph.vertices.values()) {
      this.drawVertex(ctx, vertex);
    }
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
    this.screenCenter = { x: rect.width / 2, y: rect.height / 2 };
    ctx.setTransform(ratio, 0, 0, ratio, 0, 0);
  }

  private drawEdge(ctx: CanvasRenderingContext2D, edge: Edge): void {
    if (!this.graph) return;
    const v1 = this.graph.vertices.get(edge.v1);
    const v2 = this.graph.vertices.get(edge.v2);
    if (!v1 || !v2) return;
    const p1 = this.viewportTransform(v1);
    const p2 = this.viewportTransform(v2);
    ctx.strokeStyle = edge.selected ? SELECTED_EDGE : rgb(edge.color, EDGE_COLOR);
    ctx.fillStyle = ctx.strokeStyle;
    if (edge.classname === "TriangleEdge") {
      this.drawTriangleEdge(ctx, p1, p2, edge.width || 10);
      return;
    }
    ctx.lineWidth = edge.classname === "BasicStrokeEdge" ? Math.max(1, edge.width) : 1;
    ctx.beginPath();
    ctx.moveTo(p1.x, p1.y);
    ctx.lineTo(p2.x, p2.y);
    ctx.stroke();
  }

  private drawTriangleEdge(ctx: CanvasRenderingContext2D, from: CanvasPoint, to: CanvasPoint, width: number): void {
    const dx = from.x - to.x;
    const dy = from.y - to.y;
    const distance = Math.hypot(dx, dy) || 1;
    const nx = dx / distance;
    const ny = dy / distance;
    const halfWidth = width / 2;
    const ox = -ny * halfWidth;
    const oy = nx * halfWidth;
    ctx.beginPath();
    ctx.moveTo(to.x, to.y);
    ctx.lineTo(from.x + ox, from.y + oy);
    ctx.lineTo(from.x - ox, from.y - oy);
    ctx.closePath();
    ctx.fill();
  }

  private drawVertex(ctx: CanvasRenderingContext2D, vertex: Vertex): void {
    const position = this.viewportTransform(vertex);
    if (vertex.classname === "CircleVertex") {
      this.drawCircleVertex(ctx, vertex, position);
      return;
    }
    const label = vertex.label || vertex.id;
    ctx.font = "13px system-ui, sans-serif";
    const metrics = ctx.measureText(label);
    const textHeight = Math.ceil((metrics.actualBoundingBoxAscent || 10) + (metrics.actualBoundingBoxDescent || 3));
    const width = label ? metrics.width + BOX_MARGIN * 2 : BOX_MARGIN * 2;
    const height = label ? textHeight + BOX_MARGIN * 2 : BOX_MARGIN * 2;
    ctx.fillStyle = vertex.selected ? SELECTED_FILL : VERTEX_FILL;
    ctx.strokeStyle = vertex.pinned ? PINNED_FRAME : VERTEX_FRAME;
    ctx.lineWidth = vertex.pinned ? 2.5 : 1.5;
    const left = position.x - width / 2;
    const top = position.y - height / 2;
    ctx.fillRect(left, top, width, height);
    ctx.strokeRect(left, top, width, height);
    ctx.fillStyle = "#111820";
    ctx.textAlign = "left";
    ctx.textBaseline = "alphabetic";
    if (label) {
      ctx.fillText(label, left + BOX_MARGIN, top + BOX_MARGIN + (metrics.actualBoundingBoxAscent || 10));
    }
    if (vertex.pinned) {
      this.drawPinMark(ctx, left + width - 3, top + 3);
    }
  }

  private drawCircleVertex(ctx: CanvasRenderingContext2D, vertex: Vertex, position: CanvasPoint): void {
    const radius = CIRCLE_DIAMETER / 2;
    ctx.fillStyle = vertex.selected ? SELECTED_FILL : VERTEX_FILL;
    ctx.strokeStyle = vertex.pinned ? PINNED_FRAME : VERTEX_FRAME;
    ctx.lineWidth = vertex.pinned ? 2.5 : 1.5;
    ctx.beginPath();
    ctx.arc(position.x, position.y, radius, 0, Math.PI * 2);
    ctx.fill();
    ctx.stroke();
    if (vertex.pinned) {
      this.drawPinMark(ctx, position.x + radius - 1, position.y - radius + 1);
    }
  }

  private drawPinMark(ctx: CanvasRenderingContext2D, x: number, y: number): void {
    ctx.save();
    ctx.fillStyle = PINNED_FRAME;
    ctx.beginPath();
    ctx.arc(x, y, 3.5, 0, Math.PI * 2);
    ctx.fill();
    ctx.restore();
  }

  private mouseDown(event: MouseEvent): void {
    if (!this.graph) return;
    const screenPoint = this.eventScreenPoint(event);
    const point = this.invViewportTransform(screenPoint);
    const vertex = this.nearestVertex(screenPoint);
    this.graph.clearSelection();
    if (vertex) {
      vertex.selected = true;
      this.dragging = vertex;
      this.dragStart = point;
      this.didDrag = false;
      this.onSelect(vertex.id);
    } else {
      this.onSelect(undefined);
    }
    this.draw();
  }

  private mouseMove(event: MouseEvent): void {
    if (!this.dragging) return;
    const point = this.eventPoint(event);
    if (!this.didDrag && this.dragStart && Math.hypot(point.x - this.dragStart.x, point.y - this.dragStart.y) < 3) {
      return;
    }
    this.didDrag = true;
    this.graph?.setVertexPinned(this.dragging.id, true);
    this.dragging.x = point.x;
    this.dragging.y = point.y;
    this.graph?.syncVertexParams(this.dragging);
    this.onGraphChange();
  }

  private mouseUp(event?: MouseEvent): void {
    if (event?.shiftKey && this.didDrag && this.graph && this.dragging) {
      this.graph.setVertexPinned(this.dragging.id, false);
      this.onGraphChange();
    }
    this.dragging = undefined;
    this.dragStart = undefined;
    this.didDrag = false;
  }

  private eventPoint(event: MouseEvent): CanvasPoint {
    const rect = this.canvas.getBoundingClientRect();
    return this.invViewportTransform({
      x: event.clientX - rect.left,
      y: event.clientY - rect.top
    });
  }

  private eventScreenPoint(event: MouseEvent): CanvasPoint {
    const rect = this.canvas.getBoundingClientRect();
    return {
      x: event.clientX - rect.left,
      y: event.clientY - rect.top
    };
  }

  private nearestVertex(screenPoint: CanvasPoint): Vertex | undefined {
    if (!this.graph) return undefined;
    let nearest: Vertex | undefined;
    let nearestDistanceSq = 20 * 20;
    for (const vertex of this.graph.vertices.values()) {
      const position = this.viewportTransform(vertex);
      const distanceSq = (screenPoint.x - position.x) ** 2 + (screenPoint.y - position.y) ** 2;
      if (distanceSq < nearestDistanceSq) {
        nearest = vertex;
        nearestDistanceSq = distanceSq;
      }
    }
    return nearest;
  }

  private viewportTransform(point: CanvasPoint): CanvasPoint {
    return {
      x: this.screenCenter.x + point.x * VIEWPORT_SCALE,
      y: this.screenCenter.y + point.y * VIEWPORT_SCALE
    };
  }

  private invViewportTransform(point: CanvasPoint): CanvasPoint {
    return {
      x: (point.x - this.screenCenter.x) / VIEWPORT_SCALE,
      y: (point.y - this.screenCenter.y) / VIEWPORT_SCALE
    };
  }
}

function rgb(color: [number, number, number], fallback: string): string {
  if (!color.some((value) => value !== 0)) return fallback;
  return `rgb(${color.map((value) => Math.max(0, Math.min(255, value))).join(" ")})`;
}
