<?php declare(strict_types = 1);

namespace PwExam\Model;

final class Contact
{
    /** @var int|null */
    private $id;

    /** @var string */
    private $name;

    /** @var string */
    private $surname;

    /** @var string */
    private $phone;

    /**
     * Contact constructor.
     * @param int|null $id
     * @param string $name
     * @param string $surname
     * @param string $phone
     */
    public function __construct(?int $id, string $name, string $surname, string $phone)
    {
        $this->id = $id;
        $this->name = $name;
        $this->surname = $surname;
        $this->phone = $phone;
    }

    /**
     * @return int|null
     */
    public function getId(): ?int
    {
        return $this->id;
    }

    /**
     * @param int $id
     * @return Contact
     */
    public function setId(int $id): Contact
    {
        $this->id = $id;
        return $this;
    }

    /**
     * @return string
     */
    public function getName(): string
    {
        return $this->name;
    }

    /**
     * @param string $name
     * @return Contact
     */
    public function setName(string $name): Contact
    {
        $this->name = $name;
        return $this;
    }

    /**
     * @return string
     */
    public function getSurname(): string
    {
        return $this->surname;
    }

    /**
     * @param string $surname
     * @return Contact
     */
    public function setSurname(string $surname): Contact
    {
        $this->surname = $surname;
        return $this;
    }

    /**
     * @return string
     */
    public function getPhone(): string
    {
        return $this->phone;
    }

    /**
     * @param string $phone
     * @return Contact
     */
    public function setPhone(string $phone): Contact
    {
        $this->phone = $phone;
        return $this;
    }
}
