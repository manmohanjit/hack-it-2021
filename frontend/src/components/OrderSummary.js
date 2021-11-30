const OrderSummary = ({items}) => {
  return (
    <table className="table table-hover">
      <thead>
      <tr>
        <th>Seat</th>
        <th>Category</th>
        <th className={"text-end"}>Price</th>
      </tr>
      </thead>
      <tbody>
      {items?.length > 0 ? items.map(item => (
        <tr key={item.seat.id}>
          <td>{item.seat.label}</td>
          <td>{item.category.label}</td>
          <td className={"text-end"}>RM{item.category.price / 100}</td>
        </tr>
      )) : (
        <tr>
          <td colSpan={2}>Select a seat</td>
          <td colSpan={1} className={"text-end"}>&mdash;</td>
        </tr>
      )}
      </tbody>
      <tfoot>
      <tr>
        <th colSpan={2}>Total</th>
        <th className={"text-end"}>RM{items ? items.map(item => item.category.price / 100).reduce((a, b) => a + b, 0) : <>&mdash;</>}</th>
      </tr>
      </tfoot>
    </table>
  )
}

export default OrderSummary;
