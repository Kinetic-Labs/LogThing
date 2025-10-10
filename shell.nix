{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    gradle
  ];

  # intellij sdk manager
  # nix doesnt have jdk25 yet...
  shellHook = ''
  export JAVA_HOME="$HOME/.jdks/openjdk-25"
  export PATH="$JAVA_HOME/bin:$PATH"
  '';
}
