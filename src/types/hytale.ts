export type PlayerId = string;

export type Player = {
  id: PlayerId;
  name: string;
  ping?: number;
  level?: number;
  coins?: number;
  rank?: string;
};

export type OnlineStatus = {
  online: number;
  max: number;
};

export type ScoreboardSidebar = {
  setTitle: (title: string) => void;
  setLines: (lines: string[]) => void;
  show: () => void;
  hide: () => void;
  dispose: () => void;
};

export type CommandContext = {
  player: Player;
  args: string[];
  reply: (message: string) => void;
};

export type ServerEvents = {
  onPlayerJoin: (handler: (player: Player) => void) => void;
  onPlayerLeave: (handler: (player: Player) => void) => void;
};

export type Scheduler = {
  runRepeatingTask: (handler: () => void, intervalMs: number) => { cancel: () => void };
};

export type CommandRegistry = {
  register: (name: string, handler: (context: CommandContext) => void) => void;
};

export type Server = {
  events: ServerEvents;
  scheduler: Scheduler;
  commands: CommandRegistry;
  getOnlineStatus: () => OnlineStatus;
  createScoreboardSidebar: (player: Player) => ScoreboardSidebar;
};
