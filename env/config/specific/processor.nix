################################################
# processor.nix
#
# Configuration for LogThing's processing engine
#
# * Will move away from regex in near future
################################################

let
  # regex for extracting timestamp
  timestampPattern = ''([0-9]{4}-[0-9]{2}-[0-9]{2}_[0-9]{2}:[0-9]{2}:[0-9]{2})'';

  # regex for extracting tags (e.g., thread names)
  tagPattern = ''([A-Z0-9_]+)'';

  # regex for getting log level
  levelPattern = ''(INFO|WARNING|ERROR|DEBUG|TRACE|FATAL)'';
in
{
  inherit timestampPattern;
  inherit tagPattern;
  inherit levelPattern;
}
