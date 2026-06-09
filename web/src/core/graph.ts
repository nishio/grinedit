import type { DictName, Edge, GraphObject, GraphSummary, Law, Params, Rgb, Vertex } from "./types";

const DEFAULT_COLOR: Rgb = [0, 0, 0];

function hashId(id: string): number {
  let hash = 2166136261;
  for (const char of id) {
    hash ^= char.charCodeAt(0);
    hash = Math.imul(hash, 16777619);
  }
  return hash >>> 0;
}

function pointFromId(id: string): { x: number; y: number } {
  const hash = hashId(id);
  const angle = (hash % 360) * (Math.PI / 180);
  const radius = 90 + (hash % 70);
  return {
    x: Math.round(Math.cos(angle) * radius),
    y: Math.round(Math.sin(angle) * radius)
  };
}

function asNumber(value: unknown, fallback: number): number {
  const n = Number(value);
  return Number.isFinite(n) ? n : fallback;
}

function asRgb(value: unknown, fallback: Rgb = DEFAULT_COLOR): Rgb {
  if (Array.isArray(value) && value.length >= 3) {
    return [asNumber(value[0], fallback[0]), asNumber(value[1], fallback[1]), asNumber(value[2], fallback[2])];
  }
  return fallback;
}

export class GraphModel {
  readonly all = new Map<string, GraphObject>();
  readonly vertices = new Map<string, Vertex>();
  readonly edges = new Map<string, Edge>();
  readonly laws = new Map<string, Law>();
  readonly dictionaries = new Map<DictName, Map<string, GraphObject>>();
  private counters = new Map<string, number>();

  constructor(defaultLaws = true) {
    this.dictionaries.set("All", this.all);
    this.dictionaries.set("Vertex", this.vertices as Map<string, GraphObject>);
    this.dictionaries.set("Edge", this.edges as Map<string, GraphObject>);
    this.dictionaries.set("Law", this.laws as Map<string, GraphObject>);
    if (defaultLaws) {
      this.addLaw("PL_SpringEdge", { id: "PL_SpringEdge" });
      this.addLaw("PL_Repulsion", { id: "PL_Repulsion" });
      this.addLaw("PL_Anchor", { id: "PL_Anchor" });
    }
  }

  summary(): GraphSummary {
    return {
      vertices: this.vertices.size,
      edges: this.edges.size,
      laws: this.laws.size,
      all: this.all.size
    };
  }

  makeDict(name: DictName): Map<string, GraphObject> {
    if (this.dictionaries.has(name)) {
      throw new Error(`Dictionary already exists: ${name}`);
    }
    const dict = new Map<string, GraphObject>();
    this.dictionaries.set(name, dict);
    return dict;
  }

  getDict(name: DictName): Map<string, GraphObject> {
    const dict = this.dictionaries.get(name);
    if (!dict) {
      throw new Error(`Unknown dictionary: ${name}`);
    }
    return dict;
  }

  addExisting(dictName: DictName, id: string): void {
    const object = this.all.get(id);
    if (!object) {
      throw new Error(`Unknown object: ${id}`);
    }
    this.getDict(dictName).set(id, object);
  }

  addVertex(classname = "BoxVertex", params: Params = {}): string {
    const id = String(params.id ?? this.nextId("vertex"));
    const position = pointFromId(id);
    const vertex: Vertex = {
      id,
      classname,
      label: String(params.label ?? ""),
      x: asNumber(params.x ?? params.left, position.x),
      y: asNumber(params.y ?? params.top, position.y),
      vx: 0,
      vy: 0,
      selected: false,
      params: { ...params, id }
    };
    this.vertices.set(id, vertex);
    this.all.set(id, vertex);
    return id;
  }

  addEdge(classname = "LinearEdge", params: Params = {}): string {
    const id = String(params.id ?? this.nextId("edge"));
    const v1 = String(params.v1 ?? "");
    const v2 = String(params.v2 ?? "");
    if (!this.vertices.has(v1) || !this.vertices.has(v2)) {
      throw new Error(`Edge ${id} references missing vertices: ${v1}, ${v2}`);
    }
    const edge: Edge = {
      id,
      classname,
      v1,
      v2,
      width: asNumber(params.width, classname === "BasicStrokeEdge" ? 4 : 1),
      color: asRgb(params.color),
      selected: false,
      params: { ...params, id, v1, v2 }
    };
    this.edges.set(id, edge);
    this.all.set(id, edge);
    return id;
  }

  addLaw(classname = "PhysicalLaw", params: Params = {}): string {
    const id = String(params.id ?? classname ?? this.nextId("law"));
    const law: Law = { id, classname, params: { ...params, id } };
    this.laws.set(id, law);
    this.all.set(id, law);
    return id;
  }

  remove(dictName: DictName, id: string): void {
    if (dictName === "Vertex") {
      for (const edge of [...this.edges.values()]) {
        if (edge.v1 === id || edge.v2 === id) {
          this.remove("Edge", edge.id);
        }
      }
      this.vertices.delete(id);
      this.all.delete(id);
      return;
    }
    if (dictName === "Edge") {
      this.edges.delete(id);
      this.all.delete(id);
      return;
    }
    if (dictName === "Law") {
      this.laws.delete(id);
      this.all.delete(id);
      return;
    }
    this.getDict(dictName).delete(id);
  }

  modify(dictName: DictName, id: string, params: Params): GraphObject {
    const object = this.getObject(dictName, id);
    Object.assign(object.params, params);
    if (dictName === "Vertex") {
      const vertex = object as Vertex;
      if (params.label !== undefined) vertex.label = String(params.label);
      if (params.x !== undefined) vertex.x = asNumber(params.x, vertex.x);
      if (params.y !== undefined) vertex.y = asNumber(params.y, vertex.y);
    }
    if (dictName === "Edge") {
      const edge = object as Edge;
      if (params.width !== undefined) edge.width = asNumber(params.width, edge.width);
      if (params.color !== undefined) edge.color = asRgb(params.color, edge.color);
    }
    return object;
  }

  getObject(dictName: DictName, id: string): GraphObject {
    const object = this.getDict(dictName).get(id);
    if (!object) {
      throw new Error(`Unknown ${dictName} object: ${id}`);
    }
    return object;
  }

  clearSelection(): void {
    for (const object of this.all.values()) {
      if ("selected" in object) {
        object.selected = false;
      }
    }
  }

  private nextId(prefix: string): string {
    const next = (this.counters.get(prefix) ?? 0) + 1;
    this.counters.set(prefix, next);
    return `${prefix}${next}`;
  }
}
