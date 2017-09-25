import React, {Component} from '../com/centurylink/mdw/node/node_modules/react';
import PropTypes from '../com/centurylink/mdw/node/node_modules/prop-types';

class Bug extends Component {
    
  constructor(...args) {
    super(...args);
    this.state = {bug: {}};
  }

  componentDidMount() {
    var bugId = location.hash.substring(9);
    fetch(new Request('/mdw/services/demo/api/bugs/' + bugId, {
      method: 'GET',
      headers: {Accept: 'application/json'}
    }))
    .then(response => {
      return response.json();
    })
    .then(bug => {
      this.setState({bug: bug}); 
    });
  }
  
  render() {
    return (
      <div>
        <h2>Bug:</h2>
        <pre>{JSON.stringify(this.state.bug, null, 2)}</pre>
      </div>
    );
  }
}

Bug.contextTypes = {
  hubRoot: PropTypes.string,
  serviceRoot: PropTypes.string
};
export default Bug; 