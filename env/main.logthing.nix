################################################
# main.logthing.nix
#
# Main config file to tie
# all configuration/scripting together
################################################

let
  ltConfig = import ''config/config.logthing.nix'';
in
{
  config = ltConfig.getConfig;
}
