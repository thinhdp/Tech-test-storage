import React from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";

function RowItem({ row, columns, actions }) {
  return (
    <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
      {columns.map((column) => {
        const value = row[column.id];
        if (column.id === "action") {
          return (
            <TableCell key={column.id} align={column.align}>
              <Stack
                spacing={2}
                direction="row"
                style={{
                  display: "flex",
                  width: "100%",
                  justifyContent: "flex-end",
                }}
              >
                <Button variant="contained" onClick={() => actions.edit(row.id)}>
                  Edit
                </Button>
                <Button
                  variant="outlined"
                  onClick={() => actions.delete(row.id)}
                >
                  Delete
                </Button>
              </Stack>
            </TableCell>
          );
        }
        return (
          <TableCell key={column.id} align={column.align}>
            {column.format && typeof value === "number"
              ? column.format(value)
              : value}
          </TableCell>
        );
      })}
    </TableRow>
  );
}

export default RowItem;
