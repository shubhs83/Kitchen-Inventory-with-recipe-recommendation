import React, { useState } from 'react';
import { Card, Tabs, Tab, Button } from 'react-bootstrap';
import ShoppingListItem from './ShoppingListItem';

const ShoppingListView = ({ items, onEdit, onDelete, onTogglePurchase, onClearPurchased }) => {
  const [activeTab, setActiveTab] = useState('all');

  return (
    <Card>
      <Card.Body>
        <Tabs activeKey={activeTab} onSelect={(k) => setActiveTab(k)}>
          <Tab eventKey="all" title="All">
            {items.map(item => (
              <ShoppingListItem
                key={item.id}
                item={item}
                onEdit={onEdit}
                onDelete={onDelete}
                onTogglePurchase={onTogglePurchase}
              />
            ))}
          </Tab>
        </Tabs>

        <Button onClick={onClearPurchased}>Clear Purchased</Button>
      </Card.Body>
    </Card>
  );
};

export default ShoppingListView;
