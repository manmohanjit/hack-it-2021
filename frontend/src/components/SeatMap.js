import {useEffect, useLayoutEffect, useRef, useState} from "react";
const panzoom = require('panzoom');


const SeatMap = ({svg, title, items, categories, selected, onSelect, isDisabled, isLoading, maxItems = 10, onRefreshClick, className, ...props}) => {
  const svgRef = useRef();
  const panZoomRef = useRef();
  const [svgData, setSvgData] = useState(null);

  useLayoutEffect(() => {
    panZoomRef.current = panzoom(svgRef.current, {
      zoomSpeed: 0.2,
      maxZoom: 4,
      minZoom: 1,
      bounds: true,
      boundsPadding: 1,
      onTouch: e => {
        return false;
      }
    });

    return () => {
      panZoomRef.current.dispose();
    }
  }, []);

  useEffect(() => {
    const node = document.createElement('div');
    node.innerHTML = svg;

    const svgNode = node.querySelector('svg');

    setSvgData({
      viewBox: svgNode.getAttribute('viewBox'),
      html: svgNode.innerHTML,
    });

    return () => {
      node.remove();
    }
  }, [svg]);

  useLayoutEffect(() => {
    if(!svgRef.current || !svgData) {
      return;
    }

    const currentSvgEl = svgRef.current;

    items.forEach(item => {
      const $el = currentSvgEl.querySelector('#'+item.seat.label);

      if(selected.includes(item.id)) {
        $el.style.fill = 'black';
        $el.style.stroke = 'black'
        $el.style.opacity = 1;
      }
      else {
        if(!item.enabled || item.status === 'RESERVED') {
          $el.style.fill = 'grey';
          $el.style.stroke = 'grey';
          $el.style.opacity = .25;
        }
        else if(item.status === 'SOLD') {
          $el.style.fill = item.category.colour;
          $el.style.stroke = item.category.colour;
          $el.style.opacity = .5;
        }
        else {
          $el.style.fill = 'white';
          $el.style.stroke = item.category.colour;
          $el.style.opacity = 1;
        }
      }
    })

    const onClick = (e) => {
      if(isDisabled || isLoading || !e.target.classList.contains('seat')) {
        return;
      }

      const item = items.find(i => i.seat.label === e.target.id);

      if(!item) {
        return;
      }

      onSelect(item);
    };

    currentSvgEl.addEventListener('click', onClick);

    return () => {
      currentSvgEl.removeEventListener('click', onClick);
    };
  }, [items, selected, svgData, maxItems, onSelect, isLoading, isDisabled]);

  return (
    <div
      className="seatMap"
      {...props}
    >

      <div className="mb-2 d-flex align-items-center justify-content-between">
        <div className="btn-group">
          <button className="btn btn-outline-primary" onClick={() => panZoomRef.current.smoothZoom(20, 20, 1.25)}>+</button>
          <button className="btn btn-outline-primary" onClick={() => panZoomRef.current.smoothZoom(20, 20, 0.8)}>-</button>
          <button className="btn btn-outline-primary" onClick={() => {
            panZoomRef.current.zoomAbs( 0, 0, 1);
            panZoomRef.current.moveTo(0, 0);
          }}>100%</button>
        </div>
        <button className="btn btn-outline-primary" onClick={e => {
          if(isLoading || isDisabled) {
            return;
          }

          onRefreshClick(e);
        }}>Refresh</button>
      </div>

      {title && <h6 className="text-center mt-2">{title}</h6>}
      <div className={`svg-container ${isLoading ? 'd-none' : ''}`}>
        <svg ref={svgRef} viewBox={svgData?.viewBox} dangerouslySetInnerHTML={{__html: svgData?.html}} />
      </div>
      {isLoading && <p className="text-center my-5">Loading...</p>}

      {categories && (
        <div className="border rounded d-flex flex-wrap align-items-center justify-content-center p-2 mt-2">
          {categories.map(category => (
            <div className="my-2 mx-2 d-flex align-items-center small" key={'legend-'+category.id}>
              <span className="seat me-2" style={{"--bg": category.colour}}></span>
              {category.label} &middot; RM {category.price/100}
            </div>
          ))}

          <div className="w-100"></div>

          <div className="my-2 mx-2 d-flex align-items-center small">
            <span className="seat me-2" style={{"--bg": 'black', 'backgroundColor': 'black'}}></span>
            Your Seat
          </div>
          <div className="my-2 mx-2 d-flex align-items-center small">
            {categories.map(category => (
              <span key={'state-'+category.id} className="seat me-2 opacity-50" style={{"--bg": category.colour, 'backgroundColor': category.colour}}></span>
            ))}
            Sold
          </div>
          <div className="my-2 mx-2 d-flex align-items-center small">
            <span className="seat me-2 opacity-25" style={{"--bg": 'gray', 'backgroundColor': 'gray'}}></span>
            Unavailable
          </div>
        </div>
      )}
    </div>
  );
}

export default SeatMap;
