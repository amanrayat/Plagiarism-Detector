import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom'
import {Button} from 'react-bootstrap';

class ProfessorCoursePage extends React.Component{

  constructor(props) {
    super(props);
    this.state = {

      term: ['Fall 2017','Spring 2018','Summer 2018'],
      course: ['CS5200', 'CS5800', 'CS5500','CS5010','CS6240'],
      assignments: ['homework1', 'homework2'],

    };
  }
  courseRow(text,i){
    return (
      <option key={i} value="">{text}</option>
  );
  }
  assignmentRow(text,i) {
    return (
      < option
    key = {i}
    value = "" > {text} < /option>
  )
    ;
  }
    TermRow(text,i){
      return (
        <option key={i} value="">{text}</option>
    );
  }
  render() {
    return (

      <div className={'container text-center'}>

      <div className="form-group">
      <select className="form-control" id="exampleFormControlSelect1">
      <option disabled selected={'true'}>Select your Term</option>
    {
      this.state.term.map(this.TermRow.bind(this))
    }
  </select>
      <br/>
      <select className="form-control" id="exampleFormControlSelect1">
      <option disabled selected={'true'}>Select your Course</option>
    {

      this.state.course.map(this.courseRow.bind(this))
    }
  </select>
    <br />
    <select className="form-control" id="exampleFormControlSelect1">
    <option disabled selected={'true'} >Select your Assignment</option>
    {
      this.state.assignments.map(this.assignmentRow.bind(this))
    }
  </select>
    </div>
      <Button className={'btn btn-primary float-left'}> <Link to="viewFiles"> Check Plagarism </Link></Button>
      <Button className = {'btn - btn-primary float-right'}> Set Percentage </Button>
      <Button className={'btn'}>  <a className="nav-link" href="/logout">Log Out</a> </Button>
    </div>
  );
  }
}

export default ProfessorCoursePage
