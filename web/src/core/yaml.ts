import { dump, load } from "js-yaml";
import { GraphModel } from "./graph";
import type { Params, SerializedObject } from "./types";

type YamlMap = Record<string, unknown>;

function isRecord(value: unknown): value is YamlMap {
  return typeof value === "object" && value !== null && !Array.isArray(value);
}

function edgeParams(value: unknown): Params {
  if (Array.isArray(value)) {
    return { v1: value[0], v2: value[1] };
  }
  if (isRecord(value)) {
    return { ...value };
  }
  return {};
}

function objectEntry(value: unknown, defaultClassname: string, defaultParams: Params = {}): SerializedObject {
  if (isRecord(value) && typeof value.classname === "string") {
    const rawParams = value.params;
    return {
      classname: value.classname,
      params: Array.isArray(rawParams) ? edgeParams(rawParams) : isRecord(rawParams) ? { ...rawParams } : {}
    };
  }
  return {
    classname: defaultClassname,
    params: isRecord(value) ? { ...defaultParams, ...value } : { ...defaultParams }
  };
}

export function importYamlGraph(source: string): GraphModel {
  const data = load(source) as YamlMap | undefined;
  const graph = new GraphModel(false);
  if (!data || !isRecord(data)) return graph;

  const vertexData = isRecord(data.Vertex) ? data.Vertex : {};
  for (const [id, value] of Object.entries(vertexData)) {
    const entry = objectEntry(value, "BoxVertex");
    graph.addVertex(entry.classname, { ...entry.params, id });
  }

  const edgeData = isRecord(data.Edge) ? data.Edge : {};
  for (const [id, value] of Object.entries(edgeData)) {
    const entry = Array.isArray(value)
      ? { classname: "LinearEdge", params: edgeParams(value) }
      : objectEntry(value, "LinearEdge");
    graph.addEdge(entry.classname, { ...entry.params, id });
  }

  const lawData = isRecord(data.Law) ? data.Law : {};
  if (Object.keys(lawData).length === 0) {
    graph.addLaw("PL_SpringEdge", { id: "PL_SpringEdge" });
    graph.addLaw("PL_Repulsion", { id: "PL_Repulsion" });
    graph.addLaw("PL_Anchor", { id: "PL_Anchor" });
  } else {
    for (const [id, value] of Object.entries(lawData)) {
      const entry = objectEntry(value, id);
      graph.addLaw(entry.classname, { ...entry.params, id });
    }
  }

  return graph;
}

export function exportYamlGraph(graph: GraphModel): string {
  const root: Record<string, unknown> = {
    version: "grinedit-web-mvp",
    Vertex: {},
    Edge: {},
    Law: {}
  };
  const vertices = root.Vertex as Record<string, SerializedObject>;
  const edges = root.Edge as Record<string, SerializedObject>;
  const laws = root.Law as Record<string, SerializedObject>;

  for (const vertex of graph.vertices.values()) {
    vertices[vertex.id] = {
      classname: vertex.classname,
      params: { ...vertex.params, label: vertex.label, x: vertex.x, y: vertex.y }
    };
  }
  for (const edge of graph.edges.values()) {
    edges[edge.id] = {
      classname: edge.classname,
      params: { ...edge.params, v1: edge.v1, v2: edge.v2, width: edge.width, color: edge.color }
    };
  }
  for (const law of graph.laws.values()) {
    laws[law.id] = { classname: law.classname, params: { ...law.params } };
  }

  return dump(root, { sortKeys: true, lineWidth: 100 });
}
