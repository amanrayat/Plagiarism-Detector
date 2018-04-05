import React from 'react';
import ReactDOM from 'react-dom';

export default class DeleteUser extends React.Component {

  constructor(props) {
    super(props);
    this.state = {};
    this.state.filterText = "";
    this.state.users = [];
    this.fetchUsers = this.fetchUsers.bind(this);
  }

  componentDidMount() {
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/all')
      .then(response => response.json())
      .then(data => this.setState({users: data}));
  }

  fetchUsers() {
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/all')
      .then(response => response.json())
      .then(data => this.setState({users: data}));
  }

  handleRowDel(user) {
    fetch('http://ec2-18-191-0-180.us-east-2.compute.amazonaws.com:8080/team208/admin/user/'+user.userId, {
	       method: 'DELETE'
       }).then(this.fetchUsers);
  };

  render() {
    return (
      <div>
        <UserTable onRowDel={this.handleRowDel.bind(this)} users={this.state.users} />
      </div>
    );
  }
}

class UserTable extends React.Component {

  render() {
    var rowDel = this.props.onRowDel;
    var user = this.props.users.map(function(user) {
      return (<UserRow  user={user} onDelEvent={rowDel.bind(this)} key={user.id}/>)
    });
    return (
      <div>

        <table className="table table-bordered">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Name</th>
              <th>Role</th>
              <th>Password</th>
              <th>Email ID</th>
              <th>Delete </th>
            </tr>
          </thead>

          <tbody>
            {user}
          </tbody>

        </table>
      </div>
    );
  }
}

class UserRow extends React.Component {
  onDelEvent() {
    this.props.onDelEvent(this.props.user);
  }

  render() {

    return (
      <tr className="eachRow">
        <td> {this.props.user.userId} </td>
        <td> {this.props.user.name} </td>
        <td> {this.props.user.userRole} </td>
        <td> {this.props.user.password} </td>
        <td> {this.props.user.email} </td>
        <td className="del-cell">
          <input type="button" onClick={this.onDelEvent.bind(this)} value="Delete" className="del-btn"/>
        </td>
      </tr>
    );
  }
}
