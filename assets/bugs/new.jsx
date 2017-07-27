import React from '../com/centurylink/mdw/node/node_modules/react';
import ReactDOM from '../com/centurylink/mdw/node/node_modules/react-dom';
import Bug from './Bug.jsx';

console.log("RENDERING...");
ReactDOM.render((
    <Bug />
  ), 
  document.getElementById('mdw-tasks')
);