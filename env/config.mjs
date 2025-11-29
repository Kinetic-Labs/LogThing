//! note: the regex is temporary
export default {
  version: 0.2,

  web_port: 9595,
  log_dir:  'demo_logs',

  date_pattern:  /^(\d{4}-\d{2}-\d{2})_(\d{2}:\d{2}:\d{2})/,

  tag_pattern:   /^\d{4}-\d{2}-\d{2}_\d{2}:\d{2}:\d{2} \[([A-Z])]/,

  level_pattern: /^\d{4}-\d{2}-\d{2}_\d{2}:\d{2}:\d{2} \[[A-Z]]\s*\[([A-Z]+)\s?]/i,

  message_pattern: /^\d{4}-\d{2}-\d{2}_\d{2}:\d{2}:\d{2} \[[A-Z]]\s*\[[A-Z]+\s?]\s+(.*)/i
};
