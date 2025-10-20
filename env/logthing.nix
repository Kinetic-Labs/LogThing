let
  enableAlerts = true;
  webPort = 9595;
in
{
  config = {
    inputs = {
      file = {
        path = ''logs/'';
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
    processor = {
      timestampPattern = ''([0-9]{4}-[0-9]{2}-[0-9]{2}_[0-9]{2}:[0-9]{2}:[0-9]{2})'';
      tagPattern = ''([A-Z0-9_]+)'';
      levelPattern = ''(INFO|WARNING|ERROR|DEBUG|TRACE|FATAL)'';
    };
    web = {
      port = webPort;
    };
    alerts =
      if enableAlerts then
        {
          errorSpike = {
            condition = ''when $error_rate > 100 per min'';
            window = ''5m'';
          };
        }
      else
        null;
  };
}
