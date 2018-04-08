(function() {

class Field {
   
    static get dimensions() {
        return {
            width: 648,
            height: 324
        };
    }

    /**
     * Field constructor
     * @param {HTMLElement} container - element must have aspect ratio matching {Field.dimensions}
     */
    constructor(container) {
        this.canvas = document.createElement('canvas');

        var containerWidth = container.offsetWidth;
        var containerHeight = container.offsetHeight;

        if (containerWidth/containerHeight !== Field.dimensions.width/Field.dimensions.height)
            throw 'Field container is not the correct aspect ratio';
            
        this.canvas.width = Field.dimensions.width;
        this.canvas.height = Field.dimensions.height;
        this.canvas.style.width = containerWidth + 'px';
        this.canvas.style.height = containerHeight + 'px';
        this.canvas.id = 'field';

        container.appendChild(this.canvas);
    }

    // remove parameters, should call drawWaypoints when layers are implemented
    draw(waypoints) {
        var ctx = this.canvas.getContext('2d');

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
        ctx.fillStyle = 'green';
        ctx.fill();
        ctx.restore();

        // draw tape markings
        ctx.save();
        ctx.lineWidth = 2;
        ctx.beginPath();
        // horizontal center line
        ctx.moveTo(0,162);
        ctx.lineTo(648,162);
        // vertical center line
        ctx.moveTo(324,0);
        ctx.lineTo(324,324);
        // auto lines
        ctx.moveTo(121,0);
        ctx.lineTo(121,324);
        ctx.moveTo(527,0);
        ctx.lineTo(527,324);
        // stroke black tape markings
        ctx.strokeStyle = 'black';
        ctx.stroke();
        // draw exchange tape markings
        ctx.beginPath();
        ctx.moveTo(0,149);
        ctx.lineTo(35,149);
        ctx.lineTo(35,103);
        ctx.lineTo(0,103);
        ctx.strokeStyle = 'red';
        ctx.stroke();
        ctx.beginPath();
        ctx.moveTo(648,175);
        ctx.lineTo(613,175);
        ctx.lineTo(613,221);
        ctx.lineTo(648,221);
        ctx.strokeStyle = 'blue';
        ctx.stroke();
        ctx.restore();
    }

    drawWaypoints(waypoints) {
        
    }
};

window.Field = Field;
})();
