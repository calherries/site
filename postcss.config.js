// https://nystudio107.com/blog/speeding-up-tailwind-css-builds
// https://flaviocopes.com/tailwind-setup/

module.exports = {
  plugins: [
      require('postcss-import')({
          plugins: [
          ],
          path: ['./node_modules'],
      }),
      require('tailwindcss')('./tailwind.config.js'),
      require('postcss-preset-env')({
          autoprefixer: { },
          stage: 1,
          features: {
              'nesting-rules': true,
              'focus-within-pseudo-class': false
          }
      }),
      process.env.NODE_ENV === 'production'
        ? require('cssnano')({ preset: 'default' })
        : null,
      process.env.NODE_ENV === 'production'
        ? require('@fullhuman/postcss-purgecss')({
            content: ['*.clj'],
            // defaultExtractor: content => content.match(/[\w-/:]+(?<!:)/g) || []
          })
        : null,
  ]
};
