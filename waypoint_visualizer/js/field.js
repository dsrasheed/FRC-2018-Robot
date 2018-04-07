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
        ctx.moveTo(0,29.69);
        ctx.lineTo(36,0);
        ctx.lineTo(612,0);
        ctx.lineTo(648,29.69);
        ctx.lineTo(648,294.31);
        ctx.lineTo(612,324);
        ctx.lineTo(36,324);
        ctx.lineTo(0,294.31);
        ctx.closePath();
        ctx.fillStyle = 'green';
        ctx.fill();
        ctx.restore();

        // draw tape markings
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(
    }

    drawWaypoints(waypoints) {
        
    }
};

window.Field = Field;
})();
