import React, {Component} from '../com/centurylink/mdw/node/node_modules/react';
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
    var ok = false;
    if (event.currentTarget.name === 'save') {
      fetch(new Request('/mdw/services/demo/api/bugs/' + this.state.bug.id, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.bug)
      }))
      .then(response => {
        ok = response.ok;
        return response.json();
      })
      .then(json => {
        if (ok) {
          $mdwUi.showMessage('Bug saved');
          setTimeout(() => {
            $mdwUi.clearMessage();
          }, 1500);
        }
        else {
          $mdwUi.showMessage(json.status.message);
        }
      });
    }
    else if (event.currentTarget.name === 'resolve') {
      fetch(new Request('/mdw/services/Tasks/' +  this.state.bug.id + '/Resolve', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({
          action: 'Resolve',
          user: $mdwUi.authUser.cuid, 
          taskInstanceId: this.state.bug.id})
      }))
      .then(response => {
        ok = response.ok;
        return response.json();
      })
      .then(json => {
        if (ok) {
          $mdwUi.clearMessage();
          this.loadActions();
          this.props.refreshTask(this.props.task.id);
        }
        else {
          $mdwUi.showMessage(json.status.message);
        }
      });
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

export default Bug; 