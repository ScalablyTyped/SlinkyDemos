#!/usr/bin/env bash
set -e

mkdir -p dist

# update basename on demos using react-router-dom, necessary to deploy them on a prefix path
sed 's/basename(basename)/basename("\/material-ui")/g' -i material-ui/src/main/scala/demo/Demo.scala
sed 's/BrowserRouter(/BrowserRouter("\/react-router-dom")(/g' -i react-router-dom/src/main/scala/demo/App.scala

# let's build the projects, ignore the ones causing errors
sbt fastOptJS::webpack || true
# sometimes the process is unexpectedly sent to the background
fg || true

mv antd/target/scala-2.13/scalajs-bundler/main dist/antd || true
rm -rf dist/antd/yarn.lock dist/antd/node_modules

mv cytoscape/target/scala-2.13/scalajs-bundler/main dist/cytoscape || true
rm -rf dist/cytoscape/yarn.lock dist/cytoscape/node_modules

mv downshift/target/scala-2.13/scalajs-bundler/main dist/downshift || true
rm -rf dist/downshift/yarn.lock dist/downshift/node_modules

mv fluentui/target/scala-2.13/scalajs-bundler/main dist/fluentui || true
rm -rf dist/fluentui/yarn.lock dist/fluentui/node_modules

mv gojs/target/scala-2.13/scalajs-bundler/main dist/gojs || true
rm -rf dist/gojs/yarn.lock dist/gojs/node_modules

mv material-ui/target/scala-2.13/scalajs-bundler/main dist/material-ui || true
rm -rf dist/material-ui/yarn.lock dist/material-ui/node_modules

mv monaco/target/scala-2.13/scalajs-bundler/main dist/monaco || true
rm -rf dist/monaco/yarn.lock dist/monaco/node_modules

mv nivo/target/scala-2.13/scalajs-bundler/main dist/nivo || true
rm -rf dist/nivo/yarn.lock dist/nivo/node_modules

mv plotly/target/scala-2.13/scalajs-bundler/main dist/plotly || true
rm -rf dist/plotly/yarn.lock dist/plotly/node_modules

mv react-big-calendar/target/scala-2.13/scalajs-bundler/main dist/react-big-calendar || true
rm -rf dist/react-big-calendar/yarn.lock dist/react-big-calendar/node_modules

mv react-dnd/target/scala-2.13/scalajs-bundler/main dist/react-dnd || true
rm -rf dist/react-dnd/yarn.lock dist/react-dnd/node_modules

mv react-i18n/target/scala-2.13/scalajs-bundler/main dist/react-i18n || true
rm -rf dist/react-i18n/yarn.lock dist/react-i18n/node_modules

mv react-leaflet/target/scala-2.13/scalajs-bundler/main dist/react-leaflet || true
rm -rf dist/react-leaflet/yarn.lock dist/react-leaflet/node_modules

mv react-mobx/target/scala-2.13/scalajs-bundler/main dist/react-mobx || true
rm -rf dist/react-mobx/yarn.lock dist/react-mobx/node_modules

mv react-router-dom/target/scala-2.13/scalajs-bundler/main dist/react-router-dom || true
rm -rf dist/react-router-dom/yarn.lock dist/react-router-dom/node_modules

mv react-select/target/scala-2.13/scalajs-bundler/main dist/react-select || true
rm -rf dist/react-select/yarn.lock dist/react-select/node_modules

mv react-slick/target/scala-2.13/scalajs-bundler/main dist/react-slick || true
rm -rf dist/react-slick/yarn.lock dist/react-slick/node_modules

mv semantic-ui-react-kitchensink/target/scala-2.13/scalajs-bundler/main dist/semantic-ui-react-kitchensink || true
rm -rf dist/semantic-ui-react-kitchensink/yarn.lock dist/semantic-ui-react-kitchensink/node_modules

mv storybook-react/target/scala-2.13/scalajs-bundler/main dist/storybook-react || true
rm -rf dist/storybook-react/yarn.lock dist/storybook-react/node_modules

