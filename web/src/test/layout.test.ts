import { describe, expect, it } from "vitest";
import { GraphModel } from "../core/graph";
import { layoutStep } from "../core/layout";

describe("layoutStep", () => {
  it("is deterministic for fixed positions", () => {
    const graph = new GraphModel(false);
    graph.addVertex("BoxVertex", { id: "v1", x: 0, y: 0 });
    graph.addVertex("BoxVertex", { id: "v2", x: 200, y: 0 });
    graph.addEdge("LinearEdge", { id: "e1", v1: "v1", v2: "v2" });

    layoutStep(graph);
    const v1 = graph.vertices.get("v1")!;
    const v2 = graph.vertices.get("v2")!;

    expect(Number.isFinite(v1.x)).toBe(true);
    expect(Number.isFinite(v2.x)).toBe(true);
    expect(v1.x).toBeCloseTo(0.7298, 4);
    expect(v2.x).toBeCloseTo(199.2702, 4);
  });
});
