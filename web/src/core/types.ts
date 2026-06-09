export type DictName = "All" | "Vertex" | "Edge" | "Law" | string;

export type Params = Record<string, unknown>;

export type Rgb = [number, number, number];

export interface Vertex {
  id: string;
  classname: string;
  label: string;
  x: number;
  y: number;
  vx: number;
  vy: number;
  pinned: boolean;
  selected: boolean;
  params: Params;
}

export interface Edge {
  id: string;
  classname: string;
  v1: string;
  v2: string;
  width: number;
  color: Rgb;
  selected: boolean;
  params: Params;
}

export interface Law {
  id: string;
  classname: string;
  params: Params;
}

export type GraphObject = Vertex | Edge | Law;

export interface SerializedObject {
  classname: string;
  params: Params;
}

export interface GraphSummary {
  vertices: number;
  edges: number;
  laws: number;
  pinned: number;
  all: number;
}
