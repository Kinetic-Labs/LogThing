################################################
# alerts.nix
#
# Configuration for LogThing's alerts
################################################

let
  condition = ''when $error_rate > 100 per min'';
  window = ''5m'';
in
{
  inherit condition;
  inherit window;
}
