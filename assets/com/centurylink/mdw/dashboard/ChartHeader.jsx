import React, {Component} from '../node/node_modules/react';
import PropTypes from '../node/node_modules/prop-types';
import PanelHeader from '../react/PanelHeader.jsx';
import HeaderLabel from '../react/HeaderLabel.jsx';
import HeaderDropdown from '../react/HeaderDropdown.jsx';
import HeaderButtons from '../react/HeaderButtons.jsx';
import HeaderButton from '../react/HeaderButton.jsx';
import HeaderPopButton from '../react/HeaderPopButton.jsx';
import SelectPop from './SelectPop.jsx';
import FilterPop from '../react/FilterPop.jsx';

class ChartHeader extends Component {

  constructor(...args) {
    super(...args);
    this.getBreakdown = this.getBreakdown.bind(this);
    this.handleDropdownSelect = this.handleDropdownSelect.bind(this);
    this.handleTopSelect = this.handleTopSelect.bind(this);
    this.handleSelectCancel = this.handleSelectCancel.bind(this);
    this.handleSelectApply = this.handleSelectApply.bind(this);
    this.handleFilterChange = this.handleFilterChange.bind(this);
    this.handleFilterClose = this.handleFilterClose.bind(this);
    this.handleFilterReset = this.handleFilterReset.bind(this);
  }

  // selected breakdown object from breakdownConfig
  getBreakdown() {
    return this.props.breakdownConfig.breakdowns.find(bd => bd.name === this.props.breakdown);
  }

  handleDropdownSelect(eventKey, dropdownId) { // eslint-disable-line no-unused-vars
    if (dropdownId === 'timespan-dropdown' && this.props.onTimespanChange) {
      this.props.onTimespanChange(eventKey);
    }
    else if (dropdownId === 'breakdown-dropdown' && this.props.onBreakdownChange) {
      this.props.onBreakdownChange(eventKey);
    }
  }

  handleTopSelect(top, isSelected) {
    if (this.props.onSelect) {
      this.props.onSelect(top, isSelected);
    }
  }

  handleSelectCancel() {
    this.refs.selectPopRef.hide();
    if (this.props.onSelectCancel) {
      this.props.onSelectCancel();
    }
  }

  handleSelectApply() {
    this.refs.selectPopRef.hide();
    if (this.props.onSelectApply) {
      this.props.onSelectApply();
    }
  }

  handleFilterChange(filters) {
    this.refs.filterPopRef.hide();
    if (this.props.onFilterChange) {
      this.props.onFilterChange(filters);
    }
  }

  handleFilterReset() {
    this.refs.filterPopRef.hide();
    if (this.props.onFilterReset) {
      this.props.onFilterReset();
    }
  }

  handleFilterClose() {
    this.refs.filterPopRef.hide();
  }

  render() {
    const breakdown = this.getBreakdown();
    return (
      <PanelHeader>
        {this.props.title &&
          <HeaderLabel title={this.props.title + ' for the:'} />
        }
        {this.props.title &&
          <HeaderDropdown id="timespan-dropdown"
            items={['Hour','Day','Week','Month']}
            selected={this.props.timespan}
            onSelect={this.handleDropdownSelect} />
        }

        {this.props.title &&
          <HeaderLabel title="by:" />
        }
        <HeaderDropdown id="breakdown-dropdown"
          items={this.props.breakdownConfig.breakdowns.map(bd => bd.name)}
          selected={this.props.breakdown}
          onSelect={this.handleDropdownSelect} />

        {breakdown.units &&
          <HeaderLabel title={(typeof breakdown.units === 'function') ? breakdown.units(this.props.filters) : breakdown.units} />
        }

        <HeaderButtons>
          {breakdown.selectField &&
            <HeaderPopButton label="Select" glyph="ok" rootClose={false} ref="selectPopRef"
              popover={
                <SelectPop label={breakdown.selectLabel}
                  units={(typeof breakdown.units === 'function') ? breakdown.units(this.props.filters) : breakdown.units}
                  tops={this.props.tops}
                  selected={this.props.selected}
                  onSelect={this.handleTopSelect}
                  onCancel={this.handleSelectCancel}
                  onApply={this.handleSelectApply} />
              } />
          }
          {this.props.breakdownConfig.filters &&
            <HeaderPopButton label="Filters" glyph="filter" rootClose={false} ref="filterPopRef"
              dirty={!this.props.isDefaultFilters}
              popover={
                <FilterPop filters={this.props.filters}
                  filterOptions={this.props.filterOptions}
                  onFilterChange={this.handleFilterChange}
                  onFilterReset={this.handleFilterReset}
                  onClose={this.handleFilterClose} />
              } />
          }
          {this.props.onDownload &&
            <HeaderButton label="Export" glyph="download-alt"
              onClick={this.props.onDownload} />
          }
          {this.props.list &&
            <HeaderButton label="List" glyph="menu-hamburger"
              onClick={() => location = this.context.hubRoot + '/' + this.props.list} />
          }
        </HeaderButtons>
      </PanelHeader>
    );
  }
}

ChartHeader.contextTypes = {
  hubRoot: PropTypes.string,
  serviceRoot: PropTypes.string
};

export default ChartHeader;
