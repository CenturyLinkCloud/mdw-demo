import React from 'react';
import ReactDOM from 'react-dom';
import Bug from './Bug.jsx';

ReactDOM.render((
  <Bug create={true} />
  ), 
  document.getElementById('mdw-tasks')
);