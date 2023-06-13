import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { selectReducer, setMemberData } from "../../features/members/memberSlice";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import RowItem from "../../components/RowItem";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router-dom";

const columns = [
  { id: "name", label: "Name", minWidth: 170 },
  { id: "email", label: "Email", minWidth: 100 },
  {
    id: "phone",
    label: "Phone",
    minWidth: 170,
    align: "right",
    format: (value) => value.toLocaleString("en-US"),
  },
  {
    id: "gender",
    label: "Gender",
    minWidth: 170,
    align: "right",
    format: (value) => value.toLocaleString("en-US"),
  },
  {
    id: "action",
    label: "Action",
    minWidth: 170,
    align: "right",
    format: (value) => value.toFixed(2),
  },
];

function createData(name, email, phone, gender, id) {
  return { name, phone, email, gender, id };
}

function Add(props) {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [data, setData] = useState([]);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { memberData } = useSelector(selectReducer);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  useEffect(() => {
    if (memberData && Array.isArray(memberData) && memberData?.length > 0) {
      const dataTemp = memberData.map((item) =>
        createData(
          `${item?.firstName} ${item?.lastName}`,
          item?.email,
          item?.phone,
          item?.gender,
          item?.id
        )
      );
      setData(dataTemp);
    } else {
      setData([]);
    }
  }, [memberData]);

  const actions = {
    edit: (id) => {
      navigate(`/empoloyee/edit/${id}`)
    },
    delete: (id) => {
      const members = [...memberData].filter((item) => item.id !== id);
      dispatch(setMemberData([...members]));
    },
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  const toAdd = () => {
    return navigate("/empoloyee/add")
  }
  return (
    <Paper sx={{ width: "100%" }}>
      <div
        style={{
          width: "100%",
          display: "flex",
          justifyContent: "flex-end",
          padding: "12px",
          boxSizing: "border-box",
        }}
      >
        <Button variant="contained" onClick={toAdd}>Add employee</Button>
      </div>
      <TableContainer>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {[...data]
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row) => {
                return (
                  <RowItem
                    key={row?.id}
                    row={row}
                    columns={columns}
                    actions={actions}
                  />
                );
              })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[1, 3, 5, 10, 25, 100]}
        component="div"
        count={[...data].length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
  );
}

export default Add;
