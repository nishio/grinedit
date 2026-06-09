import "./styles.css";
import { CommandGateway } from "./core/commands";
import { fixtures, type FixtureName } from "./core/fixtures";
import { layoutStep } from "./core/layout";
import { exportYamlGraph, importYamlGraph } from "./core/yaml";
import { CanvasView } from "./ui/canvas";

let currentFixture: FixtureName = "small";
let gateway = new CommandGateway(importYamlGraph(fixtures[currentFixture]));
let selectedId: string | undefined;
let running = false;
let frameHandle = 0;

document.querySelector<HTMLDivElement>("#app")!.innerHTML = `
  <div class="app-shell">
    <header class="toolbar">
      <div class="brand">GRINEdit Web</div>
      <label class="field">
        <span>Sample</span>
        <select id="fixture">
          <option value="small">small</option>
          <option value="small2">small2</option>
          <option value="testBasicStrokeEdge">BasicStrokeEdge</option>
        </select>
      </label>
      <button id="load">Load</button>
      <button id="step">Step</button>
      <button id="run">Run</button>
      <button id="addVertex">Add Vertex</button>
      <button id="addEdge">Add Edge</button>
    </header>
    <main class="workspace">
      <section class="canvas-pane">
        <canvas id="graphCanvas"></canvas>
      </section>
      <aside class="inspector">
        <section class="panel">
          <h2>Graph</h2>
          <dl class="metrics">
            <div><dt>Vertices</dt><dd id="vertexCount">0</dd></div>
            <div><dt>Edges</dt><dd id="edgeCount">0</dd></div>
            <div><dt>Laws</dt><dd id="lawCount">0</dd></div>
          </dl>
        </section>
        <section class="panel">
          <h2>Selected</h2>
          <pre id="selectedDetails" class="details">none</pre>
        </section>
        <section class="panel panel-grow">
          <h2>YAML</h2>
          <textarea id="yamlPreview" spellcheck="false"></textarea>
        </section>
      </aside>
    </main>
  </div>
`;

const canvas = document.querySelector<HTMLCanvasElement>("#graphCanvas")!;
const view = new CanvasView(canvas, (id) => {
  selectedId = id;
  render();
}, () => render());

const fixtureSelect = document.querySelector<HTMLSelectElement>("#fixture")!;
const runButton = document.querySelector<HTMLButtonElement>("#run")!;

document.querySelector<HTMLButtonElement>("#load")!.addEventListener("click", () => {
  currentFixture = fixtureSelect.value as FixtureName;
  gateway = new CommandGateway(importYamlGraph(fixtures[currentFixture]));
  selectedId = undefined;
  stop();
  render();
});

document.querySelector<HTMLButtonElement>("#step")!.addEventListener("click", () => {
  layoutStep(gateway.graph);
  render();
});

runButton.addEventListener("click", () => {
  running ? stop() : start();
  render();
});

document.querySelector<HTMLButtonElement>("#addVertex")!.addEventListener("click", () => {
  const id = gateway.addVertex("BoxVertex", {
    label: `v${gateway.graph.vertices.size + 1}`,
    x: 0,
    y: 0
  });
  gateway.graph.clearSelection();
  gateway.graph.vertices.get(id)!.selected = true;
  selectedId = id;
  render();
});

document.querySelector<HTMLButtonElement>("#addEdge")!.addEventListener("click", () => {
  const vertices = [...gateway.graph.vertices.keys()];
  if (vertices.length < 2) return;
  const from = selectedId && gateway.graph.vertices.has(selectedId) ? selectedId : vertices[0];
  const to = vertices.find((id) => id !== from);
  if (!to) return;
  gateway.addEdge("LinearEdge", { id: `e${gateway.graph.edges.size + 1}`, v1: from, v2: to });
  render();
});

window.addEventListener("resize", () => view.draw());

function start(): void {
  running = true;
  runButton.textContent = "Pause";
  const tick = () => {
    if (!running) return;
    layoutStep(gateway.graph);
    render();
    frameHandle = window.requestAnimationFrame(tick);
  };
  frameHandle = window.requestAnimationFrame(tick);
}

function stop(): void {
  running = false;
  runButton.textContent = "Run";
  if (frameHandle) window.cancelAnimationFrame(frameHandle);
}

function render(): void {
  view.setGraph(gateway.graph);
  const summary = gateway.graph.summary();
  text("#vertexCount", String(summary.vertices));
  text("#edgeCount", String(summary.edges));
  text("#lawCount", String(summary.laws));
  const selected = selectedId ? gateway.graph.all.get(selectedId) : undefined;
  text("#selectedDetails", selected ? JSON.stringify(selected.params, null, 2) : "none");
  document.querySelector<HTMLTextAreaElement>("#yamlPreview")!.value = exportYamlGraph(gateway.graph);
}

function text(selector: string, value: string): void {
  document.querySelector<HTMLElement>(selector)!.textContent = value;
}

render();
