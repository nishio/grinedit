import small from "../fixtures/small.yaml?raw";
import small2 from "../fixtures/small2.yaml?raw";
import sqr10 from "../fixtures/sqr10.yaml?raw";
import testBasicStrokeEdge from "../fixtures/testBasicStrokeEdge.yaml?raw";

export const fixtures = {
  small,
  small2,
  sqr10,
  testBasicStrokeEdge
};

export type FixtureName = keyof typeof fixtures;
