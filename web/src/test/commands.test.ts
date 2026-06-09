import { describe, expect, it } from "vitest";
import { CommandGateway } from "../core/commands";
import { GraphModel } from "../core/graph";

describe("CommandGateway", () => {
  it("adds, modifies, reads, and deletes graph objects", () => {
    const gateway = new CommandGateway(new GraphModel(false));
    gateway.addVertex("BoxVertex", { id: "v1", label: "A", x: 0, y: 0 });
    gateway.addVertex("CircleVertex", { id: "v2", label: "B", x: 20, y: 0 });
    gateway.addEdge("LinearEdge", { id: "e1", v1: "v1", v2: "v2" });

    expect(gateway.graph.summary()).toMatchObject({ vertices: 2, edges: 1, all: 3 });
    expect(gateway.getObjects("Vertex").v1.label).toBe("A");

    gateway.modVertex("v1", { label: "A1", x: 10 });
    expect(gateway.getVertex("v1")).toMatchObject({ label: "A1", position: [10, 0] });

    gateway.delEdge("e1");
    expect(gateway.graph.summary()).toMatchObject({ vertices: 2, edges: 0, all: 2 });
  });

  it("keeps All synchronized when deleting a vertex and its incident edges", () => {
    const gateway = new CommandGateway(new GraphModel(false));
    gateway.addVertex("BoxVertex", { id: "v1" });
    gateway.addVertex("BoxVertex", { id: "v2" });
    gateway.addEdge("LinearEdge", { id: "e1", v1: "v1", v2: "v2" });

    gateway.delVertex("v1");
    expect(gateway.graph.summary()).toMatchObject({ vertices: 1, edges: 0, all: 1 });
    expect(gateway.graph.all.has("v2")).toBe(true);
  });

  it("returns previous state for boolean toggles", () => {
    const gateway = new CommandGateway(new GraphModel(false));
    expect(gateway.autoLayout(false)).toBe(true);
    expect(gateway.autoLayout(true)).toBe(false);
    expect(gateway.pause(true)).toBe(false);
    expect(gateway.rendering(false)).toBe(true);
  });

  it("pins and unpins vertices through the gateway", () => {
    const gateway = new CommandGateway(new GraphModel(false));
    gateway.addVertex("BoxVertex", { id: "v1", pinned: true });

    expect(gateway.graph.summary()).toMatchObject({ pinned: 1 });
    expect(gateway.unpinVertex("v1")).not.toHaveProperty("pinned");
    expect(gateway.graph.summary()).toMatchObject({ pinned: 0 });

    expect(gateway.pinVertex("v1")).toMatchObject({ pinned: true });
    expect(gateway.graph.summary()).toMatchObject({ pinned: 1 });
  });
});
