# NekoFetcher

(Not too) Simple plugin to fetch and draw cute anime neko images on in-game maps.

The main idea behind this, is fun and give some anime servers (if they exist) something with they can decorate stuff and have fun with. Although that the main reason of this plugin, you're free of adding it to your server's plugin list indepently of what the server is.

Plugin works using the [nekos.life](https://nekos.life) API endpoints to get images, so if you have a outbound firewall add this site to the whitelist.

**NOTE:** This plugin is completly SFW, it doesn't and will never have a "NSFW mode" because on top of that is morbid, it breaks the Mojang's EULA.

## To-do features 

- Add support for more API's and sites like Safebooru maybe
- Add Vault support

*Yes, that's all*

## Compatibility and support

- Software: Paper and its forks, as the plugin **only** uses and support that API. You can still test on plain Spigot but no support will be provided.
- Version: 1.20.x, don't want to suffer porting it to old and deprecated versions.

## Compiling and installing

Firstly, make sure that you have Git and Java (JDK) installed.

Clone this repo using `git clone https://github.com/Vz0n/NekoFetcher`, then open a console (if on Windows use Git Bash) on the source directory and run `./gradlew build`. Next drop the jar built in `build/libs/NekoFetcher-x.x.x.jar` on your server's `plugins` folder and restart.

You should have the plugin working now. Bugs can be reported on the issues section.

## License information

This plugins is licensed under the GPLv3 license, please read [LICENSE](https://github.com/Vz0n/NekoFetcher/LICENSE) for more information.

Copyright © 2024 Vz0n. All rights reserved.
