export const datepicker = () => {
  let startDate = new Date();
  startDate.setDate(new Date().getDate() + 1);
  $('[data-toggle="datepicker"]').datepicker({
    autoPick: true,
    format: 'dd/MM/yyyy',
    startDate: startDate
  });
}

export const setStartDate = startDate => {
  const tomorrow = new Date();
  tomorrow.setDate(new Date().getDate() + 1);
  const start = buildDate(startDate);
  $('#startDate').datepicker({
    autoPick: true,
    format: 'dd/MM/yyyy',
    startDate: tomorrow,
    update: start
  });
}

export const setEndDate = endDate => {
  const tomorrow = new Date();
  tomorrow.setDate(new Date().getDate() + 1);
  const end = buildDate(endDate);
  $('#endDate').datepicker({
    autoPick: true,
    format: 'dd/MM/yyyy',
    startDate: tomorrow,
    update: end
  });
}

export const buildDate = dateString => {
  let splitter = dateString.split('/');
  return new Date(splitter[2], splitter[1], splitter[0]);
}

export const sortTable = (n) => {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("apartments-table");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc"; 
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.getElementsByTagName("tr");
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("td")[n];
      y = rows[i + 1].getElementsByTagName("td")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++; 
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
