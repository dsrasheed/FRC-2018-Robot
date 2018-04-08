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
     * @param {HTMLElement} container - element must have an aspect ratio matching {Field.dimensions}
     */
    constructor(container) {
        this.canvas = document.createElement('canvas');

        var containerWidth = container.offsetWidth;
        var containerHeight = container.offsetHeight;

        if (containerWidth/containerHeight !== Field.dimensions.width/Field.dimensions.height)
            throw 'Field container does not have the correct aspect ratio';
            
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
        ctx.fillStyle = 'green'; // 'rgb(101,99,100)';
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
        ctx.moveTo(196,96); // ctx.moveTo(196,96.25);
        ctx.lineTo(452,96); // ctx.lineTo(452,96.25);
        ctx.moveTo(196,228); // ctx.moveTo(196,227.75);
        ctx.lineTo(452,228); // ctx.lineTo(452,227.75);
        // stroke black tape
        ctx.strokeStyle = 'black';
        ctx.stroke();
        // null zone boundaries
        ctx.beginPath();
        ctx.moveTo(289,0);
        ctx.lineTo(289,94); // ctx.lineTo(289,94.25);
        ctx.lineTo(359,94); // ctx.lineTo(359,94.25);
        ctx.lineTo(359,0);
        ctx.moveTo(289,324);
        ctx.lineTo(289,230); // ctx.lineTo(289,229.75);
        ctx.lineTo(359,230); // ctx.lineTo(359,229.75);
        ctx.lineTo(359,324);
        ctx.strokeStyle = 'rgb(168,175,194)';
        ctx.stroke();
        // red power cube zone boundary
        ctx.beginPath();
        ctx.moveTo(140,141); // ctx.moveTo(140,140.5);
        ctx.lineTo(99,141); // ctx.lineTo(99,140.5);
        ctx.lineTo(99,184); // ctx.lineTo(99,183.5);
        ctx.lineTo(140,184); // ctx.lineTo(140,183.5);
        ctx.strokeStyle = 'red';
        ctx.stroke();
        // blue power cube zone boundary
        ctx.beginPath();
        ctx.moveTo(508,141); // ctx.moveTo(508,140.5);
        ctx.lineTo(549,141); // ctx.lineTo(549,140.5);
        ctx.lineTo(549,184); // ctx.lineTo(549,183.5);
        ctx.lineTo(508,184); // ctx.lineTo(508,183.5);
        ctx.strokeStyle = 'blue';
        ctx.stroke();
        // end
        ctx.restore();

        // draw switches
        ctx.save();
        ctx.lineWidth = 2;
        // switch containers
        ctx.beginPath();
        ctx.strokeStyle = 'rgb(168,175,194)';
        ctx.strokeRect(141,86,54,152); // ctx.strokeRect(141,86.25,54,151.5);
        ctx.strokeRect(453,86,54,152); // ctx.strokeRect(453,86.25,54,151.5);
        // switch platforms
        ctx.restore();
        ctx.save();
        ctx.fillStyle = 'rgb(8,8,8)';
        ctx.fillRect(144,90,48,36); // ctx.fillRect(144,90.25,48,36);
        ctx.fillRect(456,90,48,36); // ctx.fillRect(456,90.25,48,36);
        ctx.fillRect(144,198,48,36); // ctx.fillRect(144,197.75,48,36);
        ctx.fillRect(456,198,48,36); // ctx.fillRect(456,197.75,48,36);
        // switch middle bar
        ctx.fillStyle = 'rgb(168,175,194)';
        ctx.fillRect(164,124,7,76); // ctx.fillRect(164.4,124.25,7.2,75.5);
        ctx.fillRect(476,124,7,76); // ctx.fillRect(476.4,124.25,7.2,75.5);
        ctx.restore();
    }

    drawWaypoints(waypoints) {
        
    }
};

window.Field = Field;
})();
