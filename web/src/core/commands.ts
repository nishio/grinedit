import { GraphModel } from "./graph";
import type { DictName, Params } from "./types";

export class CommandGateway {
  autoLayoutEnabled = true;
  paused = false;
  renderingEnabled = true;
  graph: GraphModel;

  constructor(graph = new GraphModel()) {
    this.graph = graph;
  }

  initGraph(): number {
    this.graph = new GraphModel();
    return 0;
  }

  addVertex(vertexType: string, params: Params = {}): string {
    return this.graph.addVertex(vertexType, params);
  }

  addEdge(edgeType: string, params: Params = {}): string {
    return this.graph.addEdge(edgeType, params);
  }

  addLaw(className: string, params: Params = {}): string {
    return this.graph.addLaw(className, params);
  }

  addObject(namespace: DictName, typeName: string, params: Params = {}): string {
    if (namespace === "Vertex") return this.addVertex(typeName, params);
    if (namespace === "Edge") return this.addEdge(typeName, params);
    if (namespace === "Law") return this.addLaw(typeName, params);
    throw new Error(`addObject does not create custom dictionary objects yet: ${namespace}`);
  }

  delObject(namespace: DictName, name: string): string {
    this.graph.remove(namespace, name);
    return name;
  }

  delVertex(name: string): string {
    return this.delObject("Vertex", name);
  }

  delEdge(name: string): string {
    return this.delObject("Edge", name);
  }

  modObject(namespace: DictName, name: string, params: Params): Params {
    return this.graph.modify(namespace, name, params).params;
  }

  modVertex(name: string, params: Params): Params {
    return this.modObject("Vertex", name, params);
  }

  pinVertex(name: string): Params {
    this.graph.setVertexPinned(name, true);
    return this.getVertex(name);
  }

  unpinVertex(name: string): Params {
    this.graph.setVertexPinned(name, false);
    return this.getVertex(name);
  }

  modEdge(name: string, params: Params): Params {
    return this.modObject("Edge", name, params);
  }

  getObjects(namespace: DictName): Record<string, Params> {
    const result: Record<string, Params> = {};
    for (const [id, object] of this.graph.getDict(namespace)) {
      result[id] = { ...object.params };
    }
    return result;
  }

  getVertex(name: string): Params {
    return { ...this.graph.getObject("Vertex", name).params };
  }

  getEdge(name: string): Params {
    return { ...this.graph.getObject("Edge", name).params };
  }

  autoLayout(enabled: boolean): boolean {
    const previous = this.autoLayoutEnabled;
    this.autoLayoutEnabled = enabled;
    return previous;
  }

  pause(enabled: boolean): boolean {
    const previous = this.paused;
    this.paused = enabled;
    return previous;
  }

  rendering(enabled: boolean): boolean {
    const previous = this.renderingEnabled;
    this.renderingEnabled = enabled;
    return previous;
  }
}
