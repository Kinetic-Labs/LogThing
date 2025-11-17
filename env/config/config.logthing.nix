################################################
# config.logthing.nix
#
# More common/simple configuration for LogThing
################################################

let
  # port to serve dashboard on
  webPort = 9595;

  # enable or disable alerts
  enableAlerts = true;

  # load the scripting module
  scripting = import ''config/scripting.logthing.nix'';

  config = {
    inputs = {
      file = {
        # path to search for logs
        path = ''logs/'';

        # log levels to search for
        logKinds = [
          ''INFO''
          ''WARNING''
          ''ERROR''
          ''DEBUG''
          ''TRACE''
          ''FATAL''
        ];
      };
    };
    web = {
      port = webPort;
    };
    processor = {
      timestampPattern = scripting.processor.timestampPattern;
      tagPattern = scripting.processor.tagPattern;
      levelPattern = scripting.processor.levelPattern;
    };
    database = {
        database = scripting.database.database;
        username = scripting.database.username;
        password = scripting.database.password;
        port = scripting.database.port;
        host = scripting.database.host;
        uri = scripting.database.uri;
    };

    alerts =
      if enableAlerts then
        {
          errorSpike = {
            condition = scripting.alerts.condition;
            window = scripting.alerts.window;
          };
        }
      else
        null;
  };
in
{
  getConfig = config;
}
