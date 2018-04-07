import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';

export default class CourseList extends React.Component {

  constructor(props){
    super(props);

    this.state = {
      userID: this.props.userID,
      courses: [],
      createdCourseBy:this.props.userID,
      courseId: '',
      courseAbbr: '',
      courseLoc: '',
      courseName: '',
      courseTerm: ''
    };

    this.fetchCourses = this.fetchCourses.bind(this);

  }

  update(){
    this.setState({
      courseAbbr: this.refs.courseAbbr.value,
      courseLoc: this.refs.courseLoc.value,
      courseName: this.refs.courseName.value,
      courseTerm: this.refs.courseTerm.value,
    });
  }

  componentDidMount() {
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/allCourses')
      .then(response => response.json())
      .then(data => this.setState({courses: data}));
  }

  fetchCourses(){
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/allCourses')
      .then(response => response.json())
      .then(data => this.setState({courses: data}));
  }

  handleRowDel(course) {
    console.log("TODO DELETE COURSE ",course.courseId);
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/deletCourse?courseId='+course.courseId)
    .then(this.fetchCourses);
  }

  handleEditSubmit() {
    console.log("handleEditSubmit>courseID>",this.state.courseId);
    console.log("handleEditSubmit>courseAbbr>",this.state.courseAbbr);
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/updateCourse', {
      method: 'PUT',
       headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
       },
       body: JSON.stringify({
         courseId: this.state.courseId,
         courseAbbr: this.state.courseAbbr,
         courseLoc: this.state.courseLoc,
         courseName: this.state.courseName,
         courseTerm: this.state.courseTerm,
       })
     }).then(this.fetchCourses);
     this.setState({
       courseAbbr: '',
       courseLoc: '',
       courseName: '',
       courseTerm: '',
     })
  }

  handleRowUpdate(course) {
    console.log("handleRowUpdate>CourseID>",course.courseId);
    console.log("handleRowUpdate>courseAbbr> ",this.state.courseAbbr);
    this.setState({
      courseId: course.courseId,
      courseAbbr: course.courseAbbr,
      courseLoc: course.courseLoc,
      courseName: course.courseName,
      courseTerm: course.courseTerm,
    });
    console.log("handleRowUpdate<CourseID<",this.state.courseId);
    console.log("handleRowUpdate<courseAbbr<",this.state.courseAbbr);
  }

  render(){
    const courses = this.state.courses;
    return (
      <div className={'container col-md-6 col-md-offset-3'}>
        <h1> Course List </h1>
        <UserTable courses={this.state.courses} />
      </div>
    );
  }
}

class UserTable extends React.Component {

  render() {
    var rowDel = this.props.onRowDel;
    var rowUpdate = this.props.onRowUpdate;
    var course = this.props.courses.map(function(course) {
      return (<CourseRow
        course={course}
        key={course.id}/>)
    });
    return (
      <div>

        <table className="table table-bordered">
          <thead>
            <tr>
              <th>Course ID</th>
              <th>Course Title</th>
              <th>Course Name</th>
              <th>Term</th>
              <th>Location</th>
            </tr>
          </thead>

          <tbody>
            {course}
          </tbody>

        </table>
      </div>
    );
  }
}

class CourseRow extends React.Component {

  render() {

    return (
      <tr className="eachRow">
        <td> {this.props.course.courseId} </td>
        <td> {this.props.course.courseName} </td>
        <td> {this.props.course.courseAbbr} </td>
        <td> {this.props.course.courseTerm} </td>
        <td> {this.props.course.courseLoc} </td>
      </tr>
    );
  }
}
