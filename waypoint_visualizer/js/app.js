const container = document.getElementById('field');
const f = new Field(container);
const waypoints = [];
f.draw();
f.drawWaypoints([
    [0,30],
    [20, 40],
    [25, 36]
]);

