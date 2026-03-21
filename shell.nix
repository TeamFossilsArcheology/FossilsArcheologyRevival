{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = [
    pkgs.jdk17
    pkgs.gradle
    pkgs.git

    pkgs.xorg.libX11
    pkgs.xorg.libXcursor
    pkgs.xorg.libXrandr
    pkgs.xorg.libXi
    pkgs.xorg.libXxf86vm
    pkgs.xorg.libXinerama
    pkgs.libGL
    pkgs.glfw
  ];

  shellHook = ''
    export JAVA_HOME=${pkgs.jdk17}
    export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath [
      pkgs.libGL
      pkgs.glfw
      pkgs.xorg.libX11
      pkgs.xorg.libXcursor
      pkgs.xorg.libXrandr
      pkgs.xorg.libXi
      pkgs.xorg.libXxf86vm
      pkgs.xorg.libXinerama
    ]}:$LD_LIBRARY_PATH

    echo "Environment ready. Try ./gradlew runClient"
  '';
}
