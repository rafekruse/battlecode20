"""
Here's what this script does:
* Updates `client/visualizer/config.ts` with a new version number.
* Converts `specs.md` into a fancy specs html document (`frontend/public/specs.html`).
* Puts the javadoc in `frontend/public/javadoc/`.
* Builds the web client and copies it to `frontend/public/bc20`.

Only use this as part of following the `RELEASE.md` document. Crucially, `./gradlew publish` needs to run before this.
"""

import argparse
import subprocess

def main(version):
    update_client_version_number(version)

    fancy_specs()

    javadoc()

    client()

def update_client_version_number(version):
    with open('client/visualizer/config.ts', 'r') as f:
        client_config = f.read()
    config_lines = client_config.split('\n')
    for i in range(len(config_lines)):
        if config_lines[i].contains("Change this on each release!"):
            p = config_lines[i].split('"')
            config_lines[i] = p[0] + '"' + version + '"' + p[2]
    client_config = "\n".join(config_lines)
    with open('client/visualizer/config.ts', 'w') as f:
        f.write(client_config)
    
def fancy_specs():
    os.chdir('specs')
    subprocess.call('pandoc specs.md -s --template template.html --toc -o specs.html --metadata pagetitle="Battlecode 2020 Specs"', shell=True)
    os.chdir('..')
    subprocess.call('cp specs/specs.html frontend/public/specs.html')

def javadoc():
    """
    Copy javadoc
    """
    subprocess.call("cp -r engine/build/docs/javadoc frontend/public", shell=True)

def client():
    """
    Build client for web.
    """
    os.chdir("client/visualizer")
    subprocess.call("npm run prod", shell=True)
    subprocess.call("cp -r bc20 ../../frontend/public", shell=True)
    os.chdir("../../frontend")

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter
    )
    parser.add_argument('version', help='Version number, e.g. 2020.0.1.1')

    args = parser.parse_args()

    main(args.version)
