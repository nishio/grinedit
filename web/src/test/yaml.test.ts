import { describe, expect, it } from "vitest";
import { fixtures } from "../core/fixtures";
import { exportYamlGraph, importYamlGraph } from "../core/yaml";

describe("rev194 YAML fixtures", () => {
  it("imports the small fixture", () => {
    const graph = importYamlGraph(fixtures.small);
    expect(graph.summary()).toMatchObject({ vertices: 4, edges: 3, laws: 3, all: 10 });
    expect(graph.vertices.get("v1")?.label).toBe("Hello");
    expect(graph.edges.get("e1")).toMatchObject({ v1: "v1", v2: "v2", classname: "LinearEdge" });
  });

  it("imports classname and params forms", () => {
    const graph = importYamlGraph(fixtures.small2);
    expect(graph.vertices.get("v4")?.classname).toBe("CircleVertex");
    expect(graph.edges.get("e2")).toMatchObject({ classname: "TriangleEdge", v1: "v2", v2: "v3" });
  });

  it("imports BasicStrokeEdge width", () => {
    const graph = importYamlGraph(fixtures.testBasicStrokeEdge);
    expect(graph.edges.get("e1")).toMatchObject({ classname: "BasicStrokeEdge", width: 2 });
    expect(graph.edges.get("e3")).toMatchObject({ classname: "BasicStrokeEdge", width: 1.1 });
  });

  it("round-trips a normalized export", () => {
    const graph = importYamlGraph(fixtures.small2);
    const exported = exportYamlGraph(graph);
    const imported = importYamlGraph(exported);
    expect(imported.summary()).toEqual(graph.summary());
    expect(imported.vertices.get("v4")?.classname).toBe("CircleVertex");
    expect(imported.edges.get("e2")?.classname).toBe("TriangleEdge");
  });
});
