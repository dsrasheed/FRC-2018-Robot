/**
 * Field constructor
 * @param {HTMLElement} container - element to contain field
 */
function Field(container) {
    var scaleRatio, viewport, staticLayer, wpLayer, sceneCanvas;

    // creation and sizing of the field and its layers
    scaleRatio = parseInt(container.getAttribute('scale')) || 1;
    viewport = new Concrete.Viewport({
        container: container,
        width: scaleRatio * Field.width,
        height: scaleRatio * Field.height
    });
    this.scaleRatio = scaleRatio;

    staticLayer = new Concrete.Layer();
    wpLayer = new Concrete.Layer();

    viewport.add(staticLayer);
    viewport.add(wpLayer);

    staticLayer.scene.context.scale(scaleRatio, scaleRatio);
    wpLayer.scene.context.scale(scaleRatio, scaleRatio);

    // event handlers
    sceneCanvas = viewport.scene.canvas;  
    sceneCanvas.addEventListener('mousedown', this.onMouseDown.bind(this));
    sceneCanvas.addEventListener('mousemove', this.onMouseMove.bind(this));
    sceneCanvas.addEventListener('mouseup', this.onMouseUp.bind(this));
    sceneCanvas.addEventListener('mouseleave', this.onMouseUp.bind(this));
    addEventListener('keydown', this.onDeleteKeyDown.bind(this));

    // bind viewport and layers to object
    this.viewport = viewport;
    this.wpLayer = wpLayer;
    this.staticLayer = staticLayer;

    this.waypoints = [];
    this.keyToWaypoint = {};
}

Object.defineProperty(Field, 'width', {
    writable: false,
    configurable: false,
    value: 648
});

Object.defineProperty(Field, 'height', {
    writable: false,
    configurable: false,
    value: 324
});

Field.prototype = {

    /*
     * Draw the static elements of the field
     */
    draw() {
        var ctx = this.staticLayer.scene.context;

        // draw floor
        ctx.save();
        ctx.moveTo(0,30);
        ctx.lineTo(36,0);
        ctx.lineTo(612,0);
        ctx.lineTo(648,30);
        ctx.lineTo(648,294);
        ctx.lineTo(612,324);
        ctx.lineTo(36,324);
        ctx.lineTo(0,294);
        ctx.closePath();
        ctx.fillStyle = 'rgb(101,99,100)';
        ctx.fill();
        ctx.restore();

        // draw tape
        ctx.save();
        ctx.lineWidth = 2;
        ctx.beginPath();
        // horizontal center tape
        ctx.moveTo(0,162);
        ctx.lineTo(648,162);
        // vertical center tape
        ctx.moveTo(324,0);
        ctx.lineTo(324,324);
        // auto line tape
        ctx.moveTo(121,0);
        ctx.lineTo(121,324);
        ctx.moveTo(527,0);
        ctx.lineTo(527,324);
        // exchange boundaries
        ctx.moveTo(0,149);
        ctx.lineTo(35,149);
        ctx.lineTo(35,103);
        ctx.lineTo(0,103);
        ctx.moveTo(648,175);
        ctx.lineTo(613,175);
        ctx.lineTo(613,221);
        ctx.lineTo(648,221);
        // platform zone boundaries
        ctx.moveTo(196,96);
        ctx.lineTo(452,96);
        ctx.moveTo(196,228);
        ctx.lineTo(452,228);
        // stroke black tape
        ctx.strokeStyle = 'black';
        ctx.stroke();
        // null zone boundaries
        ctx.beginPath();
        ctx.moveTo(289,0);
        ctx.lineTo(289,94);
        ctx.lineTo(359,94);
        ctx.lineTo(359,0);
        ctx.moveTo(289,324);
        ctx.lineTo(289,230);
        ctx.lineTo(359,230);
        ctx.lineTo(359,324);
        ctx.strokeStyle = 'rgb(168,175,194)';
        ctx.stroke();
        // red power cube zone boundary
        ctx.beginPath();
        ctx.moveTo(140,141);
        ctx.lineTo(99,141);
        ctx.lineTo(99,184);
        ctx.lineTo(140,184);
        ctx.strokeStyle = 'red';
        ctx.stroke();
        // blue power cube zone boundary
        ctx.beginPath();
        ctx.moveTo(508,141);
        ctx.lineTo(549,141);
        ctx.lineTo(549,184);
        ctx.lineTo(508,184);
        ctx.strokeStyle = 'blue';
        ctx.stroke();
        ctx.restore();

        // draw switches
        ctx.save();
        ctx.lineWidth = 2;
        // switch containers
        ctx.strokeStyle = 'rgb(168,175,194)';
        ctx.strokeRect(141,86,54,152);
        ctx.strokeRect(453,86,54,152);
        // switch platforms
        ctx.restore();
        ctx.save();
        ctx.fillStyle = 'rgb(8,8,8)';
        ctx.fillRect(144,90,48,36);
        ctx.fillRect(456,90,48,36);
        ctx.fillRect(144,198,48,36);
        ctx.fillRect(456,198,48,36);
        // switch middle bar
        ctx.fillStyle = 'rgb(168,175,194)';
        ctx.fillRect(162,124,12,76);
        ctx.fillRect(474,124,12,76);
        ctx.restore();

        // draw platforms
        ctx.save();
        ctx.fillStyle = 'red';
        ctx.fillRect(261,97,63,130);
        ctx.fillStyle = 'blue';
        ctx.fillRect(324,97,63,130);
        ctx.restore();
        
        // draw scale
        ctx.save();
        // scale platform
        ctx.beginPath();
        ctx.fillStyle = 'rgb(8,8,8)';
        ctx.strokeStyle = 'black'
        ctx.lineWidth = 1;
        ctx.rect(300,72,48,36);
        ctx.rect(300,216,48,36);
        ctx.stroke();
        ctx.fill();
        // scale middle bar
        ctx.fillStyle = 'rgb(168,175,194)';
        ctx.fillRect(318,106,12,112)
        ctx.restore();
        
        // draw platform zone cubes
        ctx.save();
        ctx.beginPath();
        for (let i = 0; i < 6; i++) {
            ctx.rect(196,85 + i*13 + i*15,11,13);
            ctx.rect(441,85 + i*13 + i*15,11,13);
        }
        ctx.fillStyle = 'yellow';
        ctx.fill();
        ctx.restore();

        this.viewport.render();
    },
    /*
     * Draw the waypoints on the field
     */
    drawWaypoints() {
        var waypoints = this.waypoints,
            sceneCtx = this.wpLayer.scene.context,
            hitCtx = this.wpLayer.hit.context;

        this.wpLayer.scene.clear();
        this.wpLayer.hit.clear();
        for (let i = 0; i < waypoints.length; i++) {
            let waypoint = waypoints[i], x = waypoint.x, y = waypoint.y;

            // draw connecting line
            if (waypoints.length - 1 - i != 0) {
                sceneCtx.beginPath();
                sceneCtx.moveTo(x,y);
                sceneCtx.lineTo(waypoints[i+1].x, waypoints[i+1].y);
                sceneCtx.lineWidth = 2;
                sceneCtx.strokeStyle = 'rgb(255,255,255,0.6)';
                sceneCtx.stroke();
            }

            // draw the waypoints
            sceneCtx.beginPath();
            sceneCtx.fillStyle = 'green';
            if (this.selectedWaypoint === waypoint)
                sceneCtx.fillStyle = 'orange';
            sceneCtx.arc(x,y,4.5,0,2 * Math.PI);
            sceneCtx.fill();

            hitCtx.beginPath();
            hitCtx.arc(x,y,4.5,0,2 * Math.PI);
            hitCtx.fillStyle = waypoint.hitColor;
            hitCtx.fill();
        }
        this.viewport.render();
    },
    /*
     * Handles mouse down, selects or creates a waypoint
     * @param {MouseEvent} evt
     */
    onMouseDown(evt) {
        var x, y, key, waypoint, heading;
        
        x = evt.offsetX / this.scaleRatio;
        y = evt.offsetY / this.scaleRatio;

        key = this.wpLayer.hit.getIntersection(x,y);
        waypoint = this.keyToWaypoint[key];

        // begin click and drag when user clicks on existing waypoint
        if (waypoint) {
            this.selectedWaypoint = waypoint;
            this.drawWaypoints();
        } 
        // create a new waypoint since click was on empty spot
        else {
            heading = 90;
            if (this.waypoints.length > 1)
                heading = this.waypoints[this.waypoints.length-1].heading;
            waypoint = new Waypoint(x,y,heading);
            this.selectedWaypoint = waypoint;
            this.add(waypoint);
        }
    },
    /*
     * Handles mouse move, drags a selected waypoint
     * @param {MouseEvent} evt
     */
    onMouseMove(evt) {
        var selected = this.selectedWaypoint;
        if (selected != null) {
            let x, y;
            x = evt.offsetX / this.scaleRatio;
            y = evt.offsetY / this.scaleRatio;
            selected.x = x;
            selected.y = y;
            this.drawWaypoints();
        }
    },
    /*
     * Handles mouse up, deselects a selected waypoint
     * @param {MouseEvent} evt
     */
    onMouseUp(evt) {
        this.selectedWaypoint = null;
        this.drawWaypoints();
    },
    /*
     * Handles delete key press, deletes the selected waypoint
     * @param {KeyboardEvent} evt
     */
    onDeleteKeyDown(evt) {
        var selected = this.selectedWaypoint;
        if (selected != null && evt.key === 'Backspace') {
            this.remove(selected);
        }
    },
    /*
     * Adds a waypoint and sets it up for hit detection
     * @param {Waypoint} waypoint - the waypoint to add
     */
    add(waypoint) {
        var key = waypoint.id,
            hit = this.wpLayer.hit;
        
        hit.registerKey(key);
        waypoint.hitColor = hit.getColorFromKey(key);
        this.keyToWaypoint[key] = waypoint;

        this.waypoints.push(waypoint);
        
        this.drawWaypoints();
    },
    /*
     * Removes a waypoint
     * @param {Waypoint} waypoint - the waypoint to remove
     */
    remove(waypoint) {
        var waypoints = this.waypoints;
        for (let i = 0; i < waypoints.length; i++) {
            if (waypoints[i] === waypoint) {
                waypoints.splice(i,1);
                this.drawWaypoints();
                return;
            }
        }
    }

};
Field.prototype.constructor = Field;


/*
 * Waypoint constructor
 * @param {Number} x - x position of point
 * @param {Number} y - y position of point
 * @param {Number} heading - heading of point
 */
function Waypoint(x,y,heading) {
    this.x = x || 0;
    this.y = y || 0;
    this.heading = heading || 90;
    this.id = Waypoint.idCounter++;
}
Waypoint.idCounter = 0;
