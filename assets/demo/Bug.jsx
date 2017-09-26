import React, {Component} from '../com/centurylink/mdw/node/node_modules/react';
import PropTypes from '../com/centurylink/mdw/node/node_modules/prop-types';
import {Button, Glyphicon} from '../com/centurylink/mdw/node/node_modules/react-bootstrap';

class Bug extends Component {
    
  constructor(...args) {
    super(...args);
    this.state = {bug: {
      description: '',
      severity: 4,
      commitId: ''
    }};
    this.handleChange = this.handleChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
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
      bug.commitId = '';
      this.setState({bug: bug}); 
    });
  }
  
  handleChange(event) {
    var newState = Object.assign({}, this.state);
    if (event.currentTarget.name === 'description') {
      newState.bug.description = event.currentTarget.value;
    }
    else if (event.currentTarget.name === 'severity') {
      newState.bug.severity = event.currentTarget.value;
    }
    else if (event.currentTarget.name === 'commitId') {
      newState.bug.commitId = event.currentTarget.value;
    }
    this.setState(newState);
  }
  
  handleClick(event) {
    if (event.currentTarget.name === 'save') {
      console.log('save...');
    }
  }
  
  render() {
    return (
      <div className="panel panel-default" style={{margin:'0 15px'}}>
        <div className="panel-heading mdw-heading">
          <div className="mdw-heading-label">
            {this.state.bug.title}
            <a href={this.context.hubRoot + '/issues/' + this.state.bug.id} className="mdw-id">
              {this.state.bug.id}
            </a>
          </div>
        </div>
        <div className="mdw-section">
          <form name="bugForm" className="form-horizontal" role="form">
            <div className="form-group">
              <label className="control-label col-xs-2">Description</label>
              <div className="col-md-10">
                <textarea className="form-control" name="description" rows={12}
                  value={this.state.bug.description} onChange={this.handleChange} style={{maxWidth:'600px'}} />
              </div>
            </div>

            <div className="form-group">
              <label className="control-label col-xs-2">Severity</label>
              <div className="col-md-10">
                <input type="number" className="form-control" name="severity" min="1" max="4" 
                  value={this.state.bug.severity} onChange={this.handleChange} style={{width:'60px'}} />
              </div>
            </div>
            
            <div className="form-group">
              <label className="control-label col-xs-2">Commit</label>
              <div className="col-md-10">
                <input type="text" className="form-control" name="commitId"
                  value={this.state.bug.commitId} onChange={this.handleChange} style={{width:'150px'}} />
              </div>
            </div>

            <div className="form-group">
              <label className="control-label col-xs-2" />
              <div className="col-md-10">
                <Button className="mdw-action-btn" name="save" bsStyle="primary" onClick={this.handleClick}>
                  <Glyphicon glyph="floppy-disk" />{' Save'}
                </Button>
                <Button className="mdw-action-btn" name="resolve" bsStyle="primary" onClick={this.handleClick}>
                  <Glyphicon glyph="ok" />{' Resolve'}
                </Button>
              </div>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

Bug.contextTypes = {
  hubRoot: PropTypes.string,
  serviceRoot: PropTypes.string
};
export default Bug; 