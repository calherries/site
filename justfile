build-css-dev:
  npx tailwindcss build tailwind.css -o resources/public/css/main.css

build-css:
  #!/usr/bin/env bash
  out=resources/public/css/main.css
  min=resources/public/css/main.min.css
  NODE_ENV=production npx tailwindcss build tailwind.css -o $out
  npx cleancss -o $min $out
  mv $min $out

build-html:
  bootleg index.clj -o index.html

dev:
  npx onchange -i index.clj -- just build-html

build-assets:
  build-css
  build-html