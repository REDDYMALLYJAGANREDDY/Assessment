import { useEffect, useState } from "react";
import "./App.css";

type Seat = {
  id: number;
  booked: boolean;
  user?: string;
};

function App() {
  const [seats, setSeats] = useState<Seat[]>([]);
  const [selected, setSelected] = useState<number[]>([]);
  const [price, setPrice] = useState(0);
  const [msg, setMsg] = useState("");

  const API = "http://localhost:8080/api";

  useEffect(() => {
    load();
  }, []);

  const load = () => {
    fetch(API + "/seats")
      .then(r => r.json())
      .then(d => {
        d.sort((a: Seat, b: Seat) => a.id - b.id);
        setSeats(d);
      });
  };

  const toggle = (id: number) => {
    const s = seats.find(x => x.id === id);
    if (s?.booked) return;

    let arr;
    if (selected.includes(id)) {
      arr = selected.filter(x => x !== id);
    } else {
      arr = [...selected, id];
    }

    setSelected(arr);
    calc(arr);
  };

  const calc = (arr: number[]) => {
    let sold = seats.filter(x => x.booked).length;
    let t = 0;

    arr.forEach(() => {
      sold++;
      if (sold <= 50) t += 50;
      else if (sold <= 80) t += 75;
      else t += 100;
    });

    setPrice(t);
  };

  const buy = () => {
    fetch(API + "/book", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        seatIds: selected,
        user: "user1"
      })
    })
      .then(r => {
        if (!r.ok) throw new Error("failed");
        return r.json();
      })
      .then(d => {
        setMsg("Booked $" + d.totalPrice);
        setTimeout(() => setMsg(""), 2000);
        setSelected([]);
        load();
      })
      .catch(() => {
        setMsg("error");
        setTimeout(() => setMsg(""), 2000);
      });
  };

  return (
    <div style={{ textAlign: "center" }}>
      {msg && <div className="toast">{msg}</div>}

      <h2>tickets</h2>

      <div className="grid">
        {seats.map(s => (
          <div
            key={s.id}
            className={`seat ${s.booked ? "booked" : ""} ${selected.includes(s.id) ? "selected" : ""}`}
            onClick={() => toggle(s.id)}
          >
            {s.id}
          </div>
        ))}
      </div>

      <div style={{ marginTop: 10 }}>
        <b>${price}</b>
      </div>

      <button onClick={buy} disabled={!selected.length}>
        buy
      </button>
    </div>
  );
}

export default App;