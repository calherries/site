build-css-dev:
  npx postcss resources/css/app-utilities.pcss -o resources/public/css/app-utilities.css
  npx postcss resources/css/app-components.pcss -o resources/public/css/app-components.css -w --verbose

build-css-prod:
  #!/usr/bin/env bash
  NODE_ENV=production npx postcss resources/css/app-utilities.pcss -o resources/public/css/app-utilities.css
  NODE_ENV=production npx postcss resources/css/app-components.pcss -o resources/public/css/app-components.css
  ls -lh resources/public/css

for:
  #!/usr/bin/env bash
  for filename in $(find . -name '*.clj'); do 
    echo $filename
  done

build-html:
  #!/usr/bin/env bash
  bb index.clj
  bootleg milo.clj -o milo.html 

dev:
  npx onchange -i $(find . -name '*.clj' -o -name '*.md') -- just build-html

build-assets:
  just build-css-prod
  just build-html

sync:
  browser-sync start --server --files "**/*.html" "**/*.css"

deploy:
  just build-css-prod
  git add -A
  git commit -m "Deployment"
  git push