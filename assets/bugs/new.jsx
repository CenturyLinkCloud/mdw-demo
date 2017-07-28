import React from '../com/centurylink/mdw/node/node_modules/react';
import ReactDOM from '../com/centurylink/mdw/node/node_modules/react-dom';
import Bug from './Bug.jsx';

var bugElem = null;
var interval = setInterval(function() {
  bugElem = document.getElementById('bug');
  if (bugElem != null) {
    clearInterval(interval);
    ReactDOM.render((
        <Bug />
      ), 
      bugElem
    );
  }
}, 100);
