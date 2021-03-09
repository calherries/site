build-css-dev:
  postcss resources/css/app-utilities.pcss -o resources/public/css/app-utilities.css
  postcss resources/css/app-components.pcss -o resources/public/css/app-components.css -w --verbose

build-css-prod:
  #!/usr/bin/env bash
  NODE_ENV=production postcss resources/css/app-utilities.pcss -o resources/public/css/app-utilities.css
  NODE_ENV=production postcss resources/css/app-components.pcss -o resources/public/css/app-components.css

build-html:
  bootleg index.clj -o index.html

dev:
  npx onchange -i *.clj *.md -- just build-html

build-assets:
  build-css
  build-html

sync:
  browser-sync start --server --files "**/*.html" "**/*.css"
