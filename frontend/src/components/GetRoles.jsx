import { useState } from "react";
import { api } from "../service/axios";

export default function RolesList() {
  const [roles, setRoles] = useState([]);
  const [error, setError] = useState(null);

  const fetchRoles = () => {
    api.get("/roles") // Use axios to make a GET request
      .then((response) => {
        setRoles(response.data); // Set the data to state
      })
      .catch((error) => {
        setError(error.message); // Handle errors
      });
  };

  return (
    <div>
      <h2>Roles List</h2>
      <button onClick={fetchRoles}>Get Roles</button>
      {error && <p>Error: {error}</p>}
      <ul>
        {roles.map(({ id, authority, description }) => (
          <li key={id}>
            {id} - {authority}: {description}
          </li>
        ))}
      </ul>
    </div>
  );
}
