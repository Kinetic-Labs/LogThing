################################################
# database.nix
#
# Handles database configuration
################################################

let
  database = "mydb";
  username = "logthing"; # suggested username
  password = "mypass";
  port = 5432;
  host = "localhost";
  # will automatically be templated
  uri = "postgresql://{#USERNAME#}:{#PASSWORD#}@{#HOST#}:{#PORT#}/{#DATABASE#}";
in
{
  inherit database;
  inherit username;
  inherit password;
  inherit port;
  inherit host;
  inherit uri;
}
