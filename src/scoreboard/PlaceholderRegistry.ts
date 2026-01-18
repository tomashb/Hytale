import { Player } from "../types/hytale";

export type PlaceholderContext = {
  player: Player;
  online: number;
  max: number;
  gameName: string;
  data: Record<string, string | number>;
};

export type PlaceholderResolver = (context: PlaceholderContext) => string | number;

export class PlaceholderRegistry {
  private readonly resolvers = new Map<string, PlaceholderResolver>();

  register(placeholder: string, resolver: PlaceholderResolver): void {
    this.resolvers.set(placeholder, resolver);
  }

  resolveLine(line: string, context: PlaceholderContext): string {
    let resolved = line;
    for (const [placeholder, resolver] of this.resolvers.entries()) {
      const value = resolver(context);
      resolved = resolved.replaceAll(`{${placeholder}}`, String(value));
    }
    return resolved;
  }
}
