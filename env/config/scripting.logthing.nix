################################################
# scripting.logthing.nix
#
# More advanced configuration for LogThing
# In future, will be useed for
# adding and modifying LogThing's functionality
################################################

let
  alerts = import ''config/specific/alerts.nix'';
  processor = import ''config/specific/processor.nix'';
in
{
  inherit alerts;
  inherit processor;
}
