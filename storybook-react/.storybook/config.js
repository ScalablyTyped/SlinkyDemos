import { configure } from '@storybook/react';

function loadStories() {
    require('../target/scala-2.13/storybook-react-fastopt.js');
}

configure(loadStories, module);
